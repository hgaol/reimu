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
  protected ReClass clazz;

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
   * 一个成员能否被调用，看
   * <p>
   * 1. 是否是public<br/>
   * 2. 如果是protected所在类是否是调用类的父类，或者在同一package下或同一个类<br/>
   * 3. 如果不是private，则看是否在同一个package下<br/>
   * 4. c和d是否是同一个类<br/>
   * </p>
   * @param d 调用类
   * @return
   */
  public boolean isAccessableTo(ReClass d) {
    if (this.isPublic()) {
      return true;
    }
    ReClass c = this.clazz;
    // 如果是protected，则当前类c是调用类d的父类，或者同一个类，或者在同一个package下
    if (this.isProtected()) {
      return d == c || d.isSubClassOf(c)
          || c.getPackageName().equals(d.getPackageName());
    }
    // 如果不是私有的，则看是否在同一个package下
    if (!this.isPrivate()) {
      return c.getPackageName().equals(d.getPackageName());
    }
    return d == c;
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
  public boolean isProtected() {
    return 0 != (this.accessFlags & AccessFlags.ACC_PROTECTED);
  }
  public boolean isFinal() {
    return 0 != (this.accessFlags & AccessFlags.ACC_FINAL);
  }
  public boolean isSyntehetic() {
    return 0 != (this.accessFlags & AccessFlags.ACC_SYNTHETIC);
  }

  public int getAccessFlags() {
    return accessFlags;
  }

  public String getName() {
    return name;
  }

  public String getDescriptor() {
    return descriptor;
  }

  public ReClass getClazz() {
    return clazz;
  }
}
