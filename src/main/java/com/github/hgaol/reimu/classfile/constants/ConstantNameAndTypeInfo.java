package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.ConstantPool;

public class ConstantNameAndTypeInfo extends ConstantMemberrefInfo {

  public ConstantNameAndTypeInfo(ConstantPool cp) {
    super(cp);
  }

  @Override
  public String toString() {
    return "ConstantNameAndTypeInfo{" +
        "classIndex=" + classIndex +
        ", nameAndTypeIndex=" + nameAndTypeIndex +
        '}';
  }
}