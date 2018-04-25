package com.github.hgaol.reimu.rtda.heap;

import com.github.hgaol.reimu.classfile.ClassFile;
import com.github.hgaol.reimu.classfile.MemberInfo;
import com.github.hgaol.reimu.classfile.attribute.CodeAttribute;
import com.github.hgaol.reimu.classfile.attribute.ConstantValueAttribute;
import com.github.hgaol.reimu.rtda.Slots;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个类主要为了将classfile的格式解析为真正运行时使用的class结构
 *
 * @author Gao Han
 * @date: 2018年04月12日
 */
public class ReClass {

  private int accessFlags;
  private String name;
  // 超类全限定名
  private String superClassName;
  private String[] interfaceNames;
  private RtConstantPool constantPool;
  private Field[] fields;
  private Method[] methods;
  private ReClassLoader loader;
  // 解析后的超类
  private ReClass superReClass;
  private ReClass[] interfaces;
  private int instanceSlotCount;
  private int staticSlotCount;
  private Slots staticVars;

  public ReClass(ClassFile cf) {
    this.accessFlags = cf.accessFlags;
    this.name = cf.getClassName();
    this.superClassName = cf.getSuperClassName();
    this.interfaceNames = cf.getInterfaceNames();
    this.constantPool = new RtConstantPool(this, cf.constantPool);
    this.fields = newFields(this, cf.getFields());
    this.methods = newMethods(this, cf.getMethods());
    // loader, superReClass, interfaces等都是在类加载的时候解析的
  }

  /**
   * @param clazz    class which fields belongs to
   * @param cfFields class file fields info
   * @return class field info
   */
  public static Field[] newFields(ReClass clazz, MemberInfo[] cfFields) {
    Field[] fields = new Field[cfFields.length];
    for (int i = 0; i < cfFields.length; i++) {
      fields[i] = new Field(clazz, cfFields[i]);
    }

    return fields;
  }

  public static Method[] newMethods(ReClass clazz, MemberInfo[] cfMethods) {
    Method[] methods = new Method[cfMethods.length];
    for (int i = 0; i < cfMethods.length; i++) {
      methods[i] = new Method(clazz, cfMethods[i]);
    }

    return methods;
  }

  public ReObject newObject() {
    return new ReObject(this);
  }

  public Method getMainMethod() {
    return this.getStaticMethod("main", "([Ljava/lang/String;)V");
  }

  public Method getStaticMethod(String name, String descriptor) {
    for (Method method : methods) {
      if (method.isStatic() &&
          method.name.equals(name) &&
          method.descriptor.equals(descriptor)) {
        return method;
      }
    }
    return null;
  }

  /**
   * Class的成员变量信息
   */
  public static class Field extends ClassMember {
    protected int constValueIndex;
    // 这个field对应的slotId
    protected int slotId;

    public Field(ReClass clazz, MemberInfo cfField) {
      this.clazz = clazz;
      this.copyMemberInfo(cfField);
      this.copyAttributes(cfField);
    }

    public int getConstValueIndex() {
      return constValueIndex;
    }

    public int getSlotId() {
      return slotId;
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
    protected int maxStack;
    protected int maxLocals;
    protected byte[] code;
    protected int argslotCount;

    public int getArgslotCount() {
      return argslotCount;
    }

    public static class MethodDescriptor {
      protected List<String> parameterTypes = new ArrayList<>();
      protected String returnType;

      /**
       * @param t 参数类型，example: Ljava/lang/String;
       */
      public void addparameterType(String t) {
        parameterTypes.add(t);
      }
    }

    public static class MethodDescriptorParser {
      protected String raw;
      protected int offset;
      protected MethodDescriptor parsed;

      /**
       * 对descriptor字符串进行解析，得到参数和返回值个数&类型
       *
       * @param descriptor example: ([Ljava/lang/String;)V
       * @return
       */
      public MethodDescriptor parse(String descriptor) {
        this.raw = descriptor;
        this.parsed = new MethodDescriptor();
        startParams();
        parseParamTypes();
        endParams();
        parseReturnType();
        finish();
        return this.parsed;
      }

      /**
       * 解析参数类型，并设置offset
       * @return
       */
      private String parseFieldType() {
        switch (readChar()) {
          case 'B':
            return "B";
          case 'C':
            return "C";
          case 'D':
            return "D";
          case 'F':
            return "F";
          case 'I':
            return "I";
          case 'J':
            return "J";
          case 'S':
            return "S";
          case 'Z':
            return "Z";
          case 'L':
            return parseObjectType();
          case '[':
            return parseArrayType();
          default:
            unreadChar();
            return "";
        }
      }

      private void startParams() {
        if ('(' != this.readChar()) {
          causePanic();
        }
      }

      /**
       * 解析参数并添加到parsed的parameterType数组中
       */
      private void parseParamTypes() {
        while (true) {
          String t = parseFieldType();
          if (t.equals("")) {
            break;
          } else {
            parsed.addparameterType(t);
          }
        }
      }

      private void endParams() {
        if (readChar() != ')') {
          causePanic();
        }
      }

      private void parseReturnType() {
        if (readChar() == 'V') {
          parsed.returnType = "V";
          return;
        }

        unreadChar();
        String t = parseFieldType();
        if (!"".equals(t)) {
          parsed.returnType = t;
          return;
        }
        causePanic();
      }

      private void finish() {
        if (offset != raw.length()) {
          causePanic();
        }
      }

      private char readChar() {
        char b = raw.charAt(offset);
        offset++;
        return b;
      }

      private void unreadChar() {
        offset--;
      }

      /**
       * 解析对象类型, example: Ljava/lang/String;
       * @return example: Ljava/lang/String;
       */
      private String parseObjectType() {
        String unread = raw.substring(offset);
        int semicolonIndex = unread.indexOf(';');
        if (semicolonIndex == -1) {
          causePanic();
          return "";
        } else {
          int start = offset - 1;
          int end = offset + semicolonIndex + 1;
          offset = end;
          return raw.substring(start, end);
        }
      }

      /**
       * 解析数组类型，example: [Ljava/lang/String;
       * @return example: [Ljava/lang/String;
       */
      private String parseArrayType() {
        int start = offset - 1;
        parseFieldType();
        int end = offset;
        return raw.substring(start, end);
      }

      private void causePanic() {
        throw new Error("BAD descriptor: " + this.raw);
      }

    }

    public Method(ReClass clazz, MemberInfo cfField) {
      this.clazz = clazz;
      this.copyMemberInfo(cfField);
      this.copyAttributes(cfField);
      this.calcArgsSlotCount();
    }

    /**
     * 计算方法参数个数，long和double个数++
     */
    public void calcArgsSlotCount() {
      MethodDescriptor parsedDescriptor = MethodUtils.parseMethodDescriptor(descriptor);
      for (String paramType : parsedDescriptor.parameterTypes) {
        argslotCount++;
        // long and double ++
        if (paramType.equals("J") || paramType.equals("D")) {
          argslotCount++;
        }
      }
      // `this` reference
      if (!isStatic()) {
        argslotCount++;
      }
    }

    public int getMaxStack() {
      return maxStack;
    }

    public int getMaxLocals() {
      return maxLocals;
    }

    public byte[] getCode() {
      return code;
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

  public ReClass setAccessFlags(int accessFlags) {
    this.accessFlags = accessFlags;
    return this;
  }

  public String getName() {
    return name;
  }

  public ReClass setName(String name) {
    this.name = name;
    return this;
  }

  public String getSuperClassName() {
    return superClassName;
  }

  public ReClass setSuperClassName(String superClassName) {
    this.superClassName = superClassName;
    return this;
  }

  public String[] getInterfaceNames() {
    return interfaceNames;
  }

  public ReClass setInterfaceNames(String[] interfaceNames) {
    this.interfaceNames = interfaceNames;
    return this;
  }

  public RtConstantPool getConstantPool() {
    return constantPool;
  }

  public ReClass setConstantPool(RtConstantPool constantPool) {
    this.constantPool = constantPool;
    return this;
  }

  public Field[] getFields() {
    return fields;
  }

  public ReClass setFields(Field[] fields) {
    this.fields = fields;
    return this;
  }

  public Method[] getMethods() {
    return methods;
  }

  public ReClass setMethods(Method[] methods) {
    this.methods = methods;
    return this;
  }

  public ReClassLoader getLoader() {
    return loader;
  }

  public ReClass setLoader(ReClassLoader loader) {
    this.loader = loader;
    return this;
  }

  public ReClass getSuperClass() {
    return superReClass;
  }

  public ReClass setSuperClass(ReClass superReClass) {
    this.superReClass = superReClass;
    return this;
  }

  public ReClass[] getInterfaces() {
    return interfaces;
  }

  public ReClass setInterfaces(ReClass[] interfaces) {
    this.interfaces = interfaces;
    return this;
  }

  public int getInstanceSlotCount() {
    return instanceSlotCount;
  }

  public ReClass setInstanceSlotCount(int instanceSlotCount) {
    this.instanceSlotCount = instanceSlotCount;
    return this;
  }

  public int getStaticSlotCount() {
    return staticSlotCount;
  }

  public ReClass setStaticSlotCount(int staticSlotCount) {
    this.staticSlotCount = staticSlotCount;
    return this;
  }

  public Slots getStaticVars() {
    return staticVars;
  }

  public ReClass setStaticVars(Slots staticVars) {
    this.staticVars = staticVars;
    return this;
  }

  /**
   * 判断当前类是否能被d类所访问<br/>
   *
   * @param d 希望访问的类
   * @return
   */
  public boolean isAccessableTo(ReClass d) {
    return this.isPublic() || this.getPackageName().equals(d.getPackageName());
  }

  public boolean isSubClassOf(ReClass other) {
    // 看链上是否有父类和other类相同
    for (ReClass spReClass = this.superReClass; spReClass != null; spReClass = spReClass.superReClass) {
      if (spReClass == other) {
        return true;
      }
    }
    return false;
  }

  public boolean isSuperClassOf(ReClass other) {
    return other.isSubClassOf(this);
  }

  public String getPackageName() {
    int i = this.name.lastIndexOf("/");
    if (i >= 0) {
      return this.name.substring(0, i);
    }
    return "";
  }

  public boolean isInstanceOf(ReClass clazz) {
    return clazz.isAssignableFrom(this);
  }

  /**
   * this是other的父类或者接口
   *
   * @param other
   * @return
   */
  public boolean isAssignableFrom(ReClass other) {
    if (this == other) {
      return true;
    }

    if (this.isInterface()) {
      return other.isImplements(this);
    } else {
      return other.isSubClassOf(this);
    }
  }

  /**
   * this implements iface
   *
   * @param iface
   * @return
   */
  public boolean isImplements(ReClass iface) {
    for (ReClass c = this; c != null; c = c.superReClass) {
      for (ReClass i : c.interfaces) {
        if (i == iface || i.isSubInterfaceOf(iface)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isSubInterfaceOf(ReClass iface) {
    for (ReClass superInterface : this.interfaces) {
      if (superInterface == iface || superInterface.isSubInterfaceOf(iface)) {
        return true;
      }
    }
    return false;
  }

  public boolean isPublic() {
    return 0 != (this.accessFlags & AccessFlags.ACC_PUBLIC);
  }

  public boolean isFinal() {
    return 0 != (this.accessFlags & AccessFlags.ACC_FINAL);
  }

  public boolean isSuper() {
    return 0 != (this.accessFlags & AccessFlags.ACC_SUPER);
  }

  public boolean isInterface() {
    return 0 != (this.accessFlags & AccessFlags.ACC_INTERFACE);
  }

  public boolean isAbstract() {
    return 0 != (this.accessFlags & AccessFlags.ACC_ABSTRACT);
  }

  public boolean isSynthetic() {
    return 0 != (this.accessFlags & AccessFlags.ACC_SYNTHETIC);
  }

  public boolean isAnnotation() {
    return 0 != (this.accessFlags & AccessFlags.ACC_ANNOTATION);
  }

  public boolean isEnum() {
    return 0 != (this.accessFlags & AccessFlags.ACC_ENUM);
  }

}

