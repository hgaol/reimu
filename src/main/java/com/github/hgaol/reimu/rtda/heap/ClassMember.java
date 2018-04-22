package com.github.hgaol.reimu.rtda.heap;

import com.github.hgaol.reimu.classfile.MemberInfo;

/**
 * @author Gao Han
 * @date: 2018年04月17日
 */
public class ClassMember {
  protected int accessFlags;
  protected String name;
  protected String descriptor;
  protected Class clazz;

  /**
   * 将classfile的字段信息读取到ClassMember中
   * @param cfInfo class file info
   */
  public void copyMemberInfo(MemberInfo cfInfo) {
    this.accessFlags = cfInfo.accessFlags;
    this.name = cfInfo.getName();
    this.descriptor = cfInfo.getDescriptor();
  }

  /**
   * 判断该成员函数/变量是否为静态的
   * @return
   */
  public boolean isStatic() {
    return 0 != (this.accessFlags & AccessFlags.ACC_STATIC);
  }

  public boolean isPublic() {
    return 0 != (this.accessFlags & AccessFlags.ACC_PUBLIC);
  }

  public boolean isPrivate() {
    return 0 != (this.accessFlags & AccessFlags.ACC_PRIVATE);
  }
  public boolean isProteded() {
    return 0 != (this.accessFlags & AccessFlags.ACC_PROTECTED);
  }
  public boolean isFinal() {
    return 0 != (this.accessFlags & AccessFlags.ACC_FINAL);
  }
  public boolean isSyntehetic() {
    return 0 != (this.accessFlags & AccessFlags.ACC_SYNTHETIC);
  }
}
