package com.github.hgaol.reimu.classfile.attribute;

import com.github.hgaol.reimu.classfile.BytesReader;

class ConstantValueAttribute implements AttributeInfo{

  private int constantValueIndex;

  public void readInfo(BytesReader reader) {
    this.constantValueIndex = reader.readUnsignedShort();
  }

  @Override
  public String toString() {
    return "ConstantValueAttribute{" +
        "constantValueIndex=" + constantValueIndex +
        '}';
  }
}