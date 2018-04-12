package com.github.hgaol.reimu.classfile.attribute;

import com.github.hgaol.reimu.classfile.BytesReader;

import java.util.Arrays;

public class LocalVariableTableAttribute implements AttributeInfo {

  private LocalVariableInfo[] localVariableTable;

  @Override
  public void readInfo(BytesReader reader) {
    int count = reader.readUnsignedShort();
    localVariableTable = new LocalVariableInfo[count];
    for (int i = 0; i < count; i++) {
      localVariableTable[i] = new LocalVariableInfo(reader);
    }
  }

  static class LocalVariableInfo {
    private int startPc;
    private int length;
    private int nameIndex;
    private int descriptorIndex;
    private int index;

    public LocalVariableInfo(BytesReader reader) {
      this.startPc = reader.readUnsignedShort();
      this.length = reader.readUnsignedShort();
      this.nameIndex = reader.readUnsignedShort();
      this.descriptorIndex = reader.readUnsignedShort();
      this.index = reader.readUnsignedShort();
    }

    @Override
    public String toString() {
      return "LocalVariableInfo{" +
          "startPc=" + startPc +
          ", length=" + length +
          ", nameIndex=" + nameIndex +
          ", descriptorIndex=" + descriptorIndex +
          ", index=" + index +
          '}';
    }
  }

  @Override
  public String toString() {
    return "LocalVariableTableAttribute{" +
        "localVariableTable=" + Arrays.toString(localVariableTable) +
        '}';
  }
}