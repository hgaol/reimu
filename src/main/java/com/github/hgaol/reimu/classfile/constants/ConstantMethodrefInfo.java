package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.ConstantPool;

public class ConstantMethodrefInfo extends ConstantMemberrefInfo {

  public ConstantMethodrefInfo(ConstantPool cp) {
    super(cp);
  }

  @Override
  public String toString() {
    return "ConstantMethodrefInfo{" +
        "classIndex=" + classIndex +
        ", nameAndTypeIndex=" + nameAndTypeIndex +
        '}';
  }
}