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
    protected ReClass clazz;

    /**
     * 返回解析过的class，如果没有加载则进行加载
     *
     * @return 解析过的class
     */
    public ReClass resolvedClass() {
      if (this.clazz == null) {
        resolveClassref();
      }
      return clazz;
    }

    /**
     * 使用该常量池所在的class的class loader加载class
     */
    private void resolveClassref() {
      ReClass d = cp.getClazz();
      ReClass c = d.getLoader().loadClass(className);
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
    private ReClass.Field field;

    public FieldRef(RtConstantPool cp, ConstantMemberrefInfo refInfo) {
      super(refInfo);
      this.cp = cp;
    }

    public ReClass.Field resolvedField() {
      if (this.field == null) {
        resolveFieldRef();
      }
      return this.field;
    }

    private void resolveFieldRef() {
      ReClass d = cp.getClazz();
      ReClass c = d.getLoader().loadClass(className);
      ReClass.Field field = lookupField(c, this.name, this.descriptor);

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
  public static ReClass.Field lookupField(ReClass c, String name, String descriptor) {
    // 在当前类中查找field
    for (ReClass.Field field : c.getFields()) {
      if (field.name.equals(name) && field.descriptor.equals(descriptor)) {
        return field;
      }
    }
    // 在interfaces中查找
    for (ReClass iface : c.getInterfaces()) {
      ReClass.Field field = lookupField(iface, name, descriptor);
      if (field != null) {
        return field;
      }
    }
    // 在超类中查找
    if (c.getSuperClass() != null) {
      ReClass.Field field = lookupField(c.getSuperClass(), name, descriptor);
      if (field != null) {
        return field;
      }
    }
    return null;
  }


  public static class MethodRef extends MemberRef {
    private ReClass.Method method;

    public MethodRef(RtConstantPool cp, ConstantMemberrefInfo refInfo) {
      super(refInfo);
      this.cp = cp;
    }

    /**
     * 根据name和descriptor以及所在的class找到对应的method
     * 并检验method是否有权被访问
     * @return method
     */
    public ReClass.Method resolvedMethod() {
      if (this.method == null) {
        resolveMethodRef();
      }
      return method;
    }

    // jvms8 5.4.3.3
    private void resolveMethodRef() {
      ReClass d = this.cp.getClazz();
      ReClass c = this.resolvedClass();

      if (c.isInterface()) {
        throw new Error("java.lang.IncompatibleClassChangeError");
      }
      ReClass.Method method = MethodUtils.lookupMethod(c, name, descriptor);
      if (method == null) {
        throw new Error("java.lang.NoSuchMethodError");
      }
      if (!method.isAccessableTo(d)) {
        throw new Error("java.lang.IllegalAccessError");
      }

      this.method = method;
    }

  }

  public static class InterfaceMethodRef extends MemberRef {
    private ReClass.Method method;

    public InterfaceMethodRef(RtConstantPool cp, ConstantMemberrefInfo refInfo) {
      super(refInfo);
      this.cp = cp;
    }

    public ReClass.Method resolvedInterfaceMethod() {
      if (this.method == null) {
        resolveInterfaceMethodRef();
      }
      return method;
    }

    /**
     * 接口的方法没有code的属性吧
     */
    private void resolveInterfaceMethodRef() {
      ReClass d = this.cp.getClazz();
      ReClass c = this.resolvedClass();

      if (!c.isInterface()) {
        throw new Error("java.lang.IncompatibleClassChangeError");
      }
      ReClass.Method method = MethodUtils.lookupInterfaceMethod(c, name, descriptor);
      if (method == null) {
        throw new Error("java.lang.NoSuchMethodError");
      }
      if (!method.isAccessableTo(d)) {
        throw new Error("java.lang.IllegalAccessError");
      }

      this.method = method;
    }

  }

}

