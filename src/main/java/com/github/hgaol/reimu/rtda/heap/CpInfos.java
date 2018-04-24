package com.github.hgaol.reimu.rtda.heap;

import com.github.hgaol.reimu.classfile.constants.ConstantClassInfo;
import com.github.hgaol.reimu.classfile.constants.ConstantMemberrefInfo;

/**
 * @author Gao Han
 * @date: 2018年04月17日
 */
public class CpInfos {

  public static class SymRef {
    protected RtConstantPool cp;
    protected String className;
    protected Class clazz;

    /**
     * 返回解析过的class，如果没有加载则进行加载
     *
     * @return 解析过的class
     */
    public Class resolvedClass() {
      if (this.clazz == null) {
        resolveClassref();
      }
      return clazz;
    }

    /**
     * 使用该常量池所在的class的class loader加载class
     */
    private void resolveClassref() {
      Class d = cp.getClazz();
      Class c = d.getLoader().loadClass(className);
      if (!c.isAccessableTo(d)) {
        throw new Error("java.lang.IllegalAccessError");
      }
      this.clazz = c;
    }

  }

  public static class ClassRef extends SymRef {

    public ClassRef(RtConstantPool cp, ConstantClassInfo classInfo) {
      this.cp = cp;
      this.className = classInfo.getValue();
    }

  }

  public static class MemberRef extends SymRef {
    protected String name;
    protected String descriptor;

    public MemberRef(ConstantMemberrefInfo refInfo) {
      this.className = refInfo.getClassName();
      this.name = refInfo.getName();
      this.descriptor = refInfo.getDescriptor();
    }

    public String getName() {
      return name;
    }

    public String getDescriptor() {
      return descriptor;
    }
  }

  public static class FieldRef extends MemberRef {
    private Class.Field field;

    public FieldRef(RtConstantPool cp, ConstantMemberrefInfo refInfo) {
      super(refInfo);
      this.cp = cp;
    }

    public Class.Field resolvedField() {
      if (this.field == null) {
        resolveFieldRef();
      }
      return this.field;
    }

    private void resolveFieldRef() {
      Class d = cp.getClazz();
      Class c = d.getLoader().loadClass(className);
      Class.Field field = lookupField(c, this.name, this.descriptor);

      if (field == null) {
        throw new Error("java.lang.NoSuchFieldError");
      }

      if (!field.isAccessableTo(d)) {
        throw new Error("java.lang.IllegalAccessError");
      }

      this.field = field;
    }

  }

  /**
   * @param c
   * @param name
   * @param descriptor
   * @return
   */
  public static Class.Field lookupField(Class c, String name, String descriptor) {
    // 在当前类中查找field
    for (Class.Field field : c.getFields()) {
      if (field.name.equals(name) && field.descriptor.equals(descriptor)) {
        return field;
      }
    }
    // 在interfaces中查找
    for (Class iface : c.getInterfaces()) {
      Class.Field field = lookupField(iface, name, descriptor);
      if (field != null) {
        return field;
      }
    }
    // 在超类中查找
    if (c.getSuperClass() != null) {
      Class.Field field = lookupField(c.getSuperClass(), name, descriptor);
      if (field != null) {
        return field;
      }
    }
    return null;
  }


  public static class MethodRef extends MemberRef {
    private Class.Method method;

    public MethodRef(RtConstantPool cp, ConstantMemberrefInfo refInfo) {
      super(refInfo);
      this.cp = cp;
    }
  }

  public static class InterfaceMethodRef extends MemberRef {
    private Class.Method method;

    public InterfaceMethodRef(RtConstantPool cp, ConstantMemberrefInfo refInfo) {
      super(refInfo);
      this.cp = cp;
    }
  }

}

