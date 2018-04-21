package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.BytesReader;
import com.github.hgaol.reimu.classfile.ConstantPool;

public class ConstantNameAndTypeInfo implements ConstantInfo {

  private int nameIndex;
  private int descriptorIndex;

  public int getNameIndex() {
    return nameIndex;
  }

  public int getDescriptorIndex() {
    return descriptorIndex;
  }

  public String toString() {
    return "ConstantNameAndTypeInfo{" +
        "nameIndex=" + nameIndex +
        ", descriptorIndex=" + descriptorIndex +
        '}';
  }

  @Override
  public void readInfo(BytesReader reader) {
    nameIndex = reader.readUnsignedShort();
    descriptorIndex = reader.readUnsignedShort();
  }
}