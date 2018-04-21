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

}
