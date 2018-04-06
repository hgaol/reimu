package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.BytesReader;

public class ConstantMethodTypeInfo implements ConstantInfo {

  private int descriptorIndex;

  @Override
  public void readInfo(BytesReader reader) {
    descriptorIndex = reader.readUnsignedShort();
  }

  @Override
  public String toString() {
    return "ConstantMethodTypeInfo{" +
        "descriptorIndex=" + descriptorIndex +
        '}';
  }
}