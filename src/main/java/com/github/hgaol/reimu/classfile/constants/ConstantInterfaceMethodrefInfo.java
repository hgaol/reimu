package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.ConstantPool;

public class ConstantInterfaceMethodrefInfo extends ConstantMemberrefInfo {

  public ConstantInterfaceMethodrefInfo(ConstantPool cp) {
    super(cp);
  }

  @Override
  public String toString() {
    return "ConstantInterfaceMethodrefInfo{" +
        "classIndex=" + classIndex +
        ", nameAndTypeIndex=" + nameAndTypeIndex +
        '}';
  }
}