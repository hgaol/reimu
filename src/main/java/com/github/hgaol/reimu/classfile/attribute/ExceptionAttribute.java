package com.github.hgaol.reimu.classfile.attribute;

import com.github.hgaol.reimu.classfile.BytesReader;

import java.util.Arrays;

public class ExceptionAttribute implements AttributeInfo{

  private int[] exceptionIndexTable;

  @Override
  public void readInfo(BytesReader reader) {
    int count = reader.readUnsignedShort();
    this.exceptionIndexTable = new int[count];
    for (int i = 0; i < count; i++) {
      exceptionIndexTable[i] = reader.readUnsignedShort();
    }
  }

  @Override
  public String toString() {
    return "ExceptionAttribute{" +
        "exceptionIndexTable=" + Arrays.toString(exceptionIndexTable) +
        '}';
  }
}