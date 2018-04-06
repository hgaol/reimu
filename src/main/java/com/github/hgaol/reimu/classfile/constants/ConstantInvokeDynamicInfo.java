package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.BytesReader;

public class ConstantInvokeDynamicInfo implements ConstantInfo {

  private int bootstrapMethodAttrIndex;
  private int nameAndTypeIndex;

  @Override
  public void readInfo(BytesReader reader) {
    bootstrapMethodAttrIndex = reader.readUnsignedShort();
    nameAndTypeIndex = reader.readUnsignedShort();
  }

  @Override
  public String toString() {
    return "ConstantInvokeDynamicInfo{" +
        "bootstrapMethodAttrIndex=" + bootstrapMethodAttrIndex +
        ", nameAndTypeIndex=" + nameAndTypeIndex +
        '}';
  }
}