package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.ConstantPool;

public class ConstantFieldrefInfo extends ConstantMemberrefInfo {

  public ConstantFieldrefInfo(ConstantPool cp) {
    super(cp);
  }

  @Override
  public String toString() {
    return "ConstantFieldrefInfo{" +
        "classIndex=" + classIndex +
        ", nameAndTypeIndex=" + nameAndTypeIndex +
        '}';
  }
}