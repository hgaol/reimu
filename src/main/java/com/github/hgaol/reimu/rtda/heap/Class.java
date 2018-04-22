package com.github.hgaol.reimu.rtda.heap;

import com.github.hgaol.reimu.classfile.ClassFile;
import com.github.hgaol.reimu.classfile.ConstantPool;
import com.github.hgaol.reimu.classfile.MemberInfo;
import com.github.hgaol.reimu.classfile.attribute.CodeAttribute;
import com.github.hgaol.reimu.classfile.attribute.ConstantValueAttribute;
import com.github.hgaol.reimu.classfile.constants.*;
import com.github.hgaol.reimu.rtda.Slots;

/**
 * 这个类主要为了将classfile的格式解析为真正运行时使用的class结构
 *
 * @author Gao Han
 * @date: 2018年04月12日
 */
public class Class {

  private int accessFlags;
  private String name;
  // 超类全限定名
  private String superClassName;
  private String[] interfaceNames;
  private RtConstantPool constantPool;
  private Field[] fields;
  private Method[] methods;
  private ClassLoader loader;
  // 解析后的超类
  private Class superClass;
  private Class[] interfaces;
  private int instanceSlotCount;
  private int staticSlotCount;
  private Slots staticVars;

  public Class(ClassFile cf) {
    this.accessFlags = cf.accessFlags;
    this.name = cf.getClassName();
    this.superClassName = cf.getClassName();
    this.interfaceNames = cf.getInterfaceNames();
    this.constantPool = new RtConstantPool(this, cf.constantPool);
    this.fields = newFields(this, cf.getFields());
    this.methods = newMethods(this, cf.getMethods());
    // loader, superClass, interfaces等都是在类加载的时候解析的
  }

  public static class RtConstantPool {
    private Class clazz;
    private Object[] constant;

    public RtConstantPool(Class clazz, ConstantPool cfCp) {
      this.clazz = clazz;
      constant = new Object[cfCp.constants.length];

      for (int i = 0; i < cfCp.constants.length; i++) {
        ConstantInfo cfInfo = cfCp.constants[i];
        ConstantType constantType = ConstantType.valueOf(cfInfo.getClass().getSimpleName());
        switch (constantType) {
          case ConstantIntegerInfo:
            constant[i] = ((ConstantIntegerInfo) cfInfo).getValue();
          case ConstantFloatInfo:
            constant[i] = ((ConstantFloatInfo) cfInfo).getValue();
          case ConstantLongInfo:
            constant[i] = ((ConstantLongInfo) cfInfo).getValue();
            i++;
          case ConstantDoubleInfo:
            constant[i] = ((ConstantDoubleInfo) cfInfo).getValue();
            i++;
          case ConstantStringInfo:
            constant[i] = ((ConstantStringInfo) cfInfo).getValue();
          case ConstantClassInfo:
            constant[i] = new CpInfos.ClassRef(this, (ConstantClassInfo) cfInfo);
          case ConstantFieldrefInfo:
            constant[i] = new CpInfos.FieldRef(this, (ConstantFieldrefInfo) cfInfo);
          case ConstantMethodrefInfo:
            constant[i] = new CpInfos.MethodRef(this, (ConstantMethodrefInfo) cfInfo);
          case ConstantInterfacemethodrefInfo:
            constant[i] = new CpInfos.InterfaceMethodRef(this, (ConstantInterfaceMethodrefInfo) cfInfo);
          default:
        }
      }

    }

    public Object getConstant(int index) {
      Object val = this.constant[index];
      if (val == null) {
        throw new Error("No constants at index " + index);
      }
      return val;
    }

    enum ConstantType {
      ConstantIntegerInfo,
      ConstantFloatInfo,
      ConstantLongInfo,
      ConstantDoubleInfo,
      ConstantStringInfo,
      ConstantClassInfo,
      ConstantFieldrefInfo,
      ConstantMethodrefInfo,
      ConstantInterfacemethodrefInfo
    }
  }

  /**
   * @param clazz    class which fields belongs to
   * @param cfFields class file fields info
   * @return class field info
   */
  public static Field[] newFields(Class clazz, MemberInfo[] cfFields) {
    Field[] fields = new Field[cfFields.length];
    for (int i = 0; i < cfFields.length; i++) {
      fields[i] = new Field(clazz, cfFields[i]);
    }

    return fields;
  }

  public static Method[] newMethods(Class clazz, MemberInfo[] cfMethods) {
    Method[] methods = new Method[cfMethods.length];
    for (int i = 0; i < cfMethods.length; i++) {
      methods[i] = new Method(clazz, cfMethods[i]);
    }

    return methods;
  }

  /**
   * Class的成员变量信息
   */
  public static class Field extends ClassMember {
    protected int constValueIndex;
    // 这个field对应的slotId
    protected int slotId;

    public Field(Class clazz, MemberInfo cfField) {
      this.clazz = clazz;
      this.copyMemberInfo(cfField);
      this.copyAttributes(cfField);
    }

    /**
     * 将field字段中的attributes读取过来，只有constValue(被定义了final关键字的成员变量)的index会保存
     * index中保存的为值的索引
     *
     * @param cfInfo class file info
     */
    public void copyAttributes(MemberInfo cfInfo) {
      ConstantValueAttribute valAttr = cfInfo.getConstantValueAttribute();
      if (valAttr != null) {
        this.constValueIndex = valAttr.getConstantValueIndex();
      }
    }

    public boolean isVolatile() {
      return 0 != (this.accessFlags & AccessFlags.ACC_VOLATILE);
    }

    public boolean isTransient() {
      return 0 != (this.accessFlags & AccessFlags.ACC_TRANSIENT);
    }

    public boolean isEnum() {
      return 0 != (this.accessFlags & AccessFlags.ACC_ENUM);
    }

    public boolean isLongOrDouble() {
      return this.descriptor.equals("J") || this.descriptor.equals("D");
    }

  }

  public static class Method extends ClassMember {
    protected long maxStack;
    protected long maxLocals;
    protected byte[] code;

    public Method(Class clazz, MemberInfo cfField) {
      this.clazz = clazz;
      this.copyMemberInfo(cfField);
      this.copyAttributes(cfField);
    }

    public void copyAttributes(MemberInfo cfMethod) {
      CodeAttribute codeAttr = cfMethod.getCodeAttribute();
      if (codeAttr != null) {
        this.maxLocals = codeAttr.maxLocals;
        this.maxStack = codeAttr.maxStack;
        this.code = codeAttr.code;
      }
    }

    public boolean isSynchronized() {
      return 0 != (this.accessFlags & AccessFlags.ACC_SYNCHRONIZED);
    }

    public boolean isBridge() {
      return 0 != (this.accessFlags & AccessFlags.ACC_BRIDGE);
    }

    public boolean isVarargs() {
      return 0 != (this.accessFlags & AccessFlags.ACC_VARAGES);
    }

    public boolean isNative() {
      return 0 != (this.accessFlags & AccessFlags.ACC_NATIVE);
    }

    public boolean isAbstract() {
      return 0 != (this.accessFlags & AccessFlags.ACC_ABSTRACT);
    }

    public boolean isStrict() {
      return 0 != (this.accessFlags & AccessFlags.ACC_STRICT);
    }

  }

  public int getAccessFlags() {
    return accessFlags;
  }

  public Class setAccessFlags(int accessFlags) {
    this.accessFlags = accessFlags;
    return this;
  }

  public String getName() {
    return name;
  }

  public Class setName(String name) {
    this.name = name;
    return this;
  }

  public String getSuperClassName() {
    return superClassName;
  }

  public Class setSuperClassName(String superClassName) {
    this.superClassName = superClassName;
    return this;
  }

  public String[] getInterfaceNames() {
    return interfaceNames;
  }

  public Class setInterfaceNames(String[] interfaceNames) {
    this.interfaceNames = interfaceNames;
    return this;
  }

  public RtConstantPool getConstantPool() {
    return constantPool;
  }

  public Class setConstantPool(RtConstantPool constantPool) {
    this.constantPool = constantPool;
    return this;
  }

  public Field[] getFields() {
    return fields;
  }

  public Class setFields(Field[] fields) {
    this.fields = fields;
    return this;
  }

  public Method[] getMethods() {
    return methods;
  }

  public Class setMethods(Method[] methods) {
    this.methods = methods;
    return this;
  }

  public ClassLoader getLoader() {
    return loader;
  }

  public Class setLoader(ClassLoader loader) {
    this.loader = loader;
    return this;
  }

  public Class getSuperClass() {
    return superClass;
  }

  public Class setSuperClass(Class superClass) {
    this.superClass = superClass;
    return this;
  }

  public Class[] getInterfaces() {
    return interfaces;
  }

  public Class setInterfaces(Class[] interfaces) {
    this.interfaces = interfaces;
    return this;
  }

  public int getInstanceSlotCount() {
    return instanceSlotCount;
  }

  public Class setInstanceSlotCount(int instanceSlotCount) {
    this.instanceSlotCount = instanceSlotCount;
    return this;
  }

  public int getStaticSlotCount() {
    return staticSlotCount;
  }

  public Class setStaticSlotCount(int staticSlotCount) {
    this.staticSlotCount = staticSlotCount;
    return this;
  }

  public Slots getStaticVars() {
    return staticVars;
  }

  public Class setStaticVars(Slots staticVars) {
    this.staticVars = staticVars;
    return this;
  }
}
