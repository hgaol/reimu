package com.github.hgaol.reimu.rtda.heap;

import com.github.hgaol.reimu.classfile.constants.ConstantClassInfo;
import com.github.hgaol.reimu.classfile.constants.ConstantMemberrefInfo;

/**
 * @author Gao Han
 * @date: 2018年04月17日
 */
public class CpInfos {

  public static class SymRef {
    protected Class.RtConstantPool cp;
    protected String className;
    protected Class clazz;
  }

  public static class ClassRef extends SymRef {

    public ClassRef(Class.RtConstantPool cp, ConstantClassInfo classInfo) {
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
  }

  public static class FieldRef extends MemberRef {
    private Class.Field field;

    public FieldRef(Class.RtConstantPool cp, ConstantMemberrefInfo refInfo) {
      super(refInfo);
      this.cp = cp;
    }
  }



  public static class MethodRef extends MemberRef {
    private Class.Method method;

    public MethodRef(Class.RtConstantPool cp, ConstantMemberrefInfo refInfo) {
      super(refInfo);
      this.cp = cp;
    }
  }

  public static class InterfaceMethodRef extends MemberRef {
    private Class.Method method;

    public InterfaceMethodRef(Class.RtConstantPool cp, ConstantMemberrefInfo refInfo) {
      super(refInfo);
      this.cp = cp;
    }
  }

}

