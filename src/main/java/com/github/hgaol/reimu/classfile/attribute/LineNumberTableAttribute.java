package com.github.hgaol.reimu.classfile.attribute;

import com.github.hgaol.reimu.classfile.BytesReader;

import java.util.Arrays;

class LineNumberTableAttribute implements AttributeInfo {

  private LineNumberInfo[] lineNumberTable;

  public void readInfo(BytesReader reader) {
    int count = reader.readUnsignedShort();
    this.lineNumberTable = new LineNumberInfo[count];
    for (int i = 0; i < count; i++) {
      this.lineNumberTable[i] = new LineNumberInfo(reader);
    }
  }

  static class LineNumberInfo {
    private int startPc;
    private int lineNumber;

    public LineNumberInfo(BytesReader reader) {
      this.startPc = reader.readUnsignedShort();
      this.lineNumber = reader.readUnsignedShort();
    }

    @Override
    public String toString() {
      return "LineNumberInfo{" +
          "startPc=" + startPc +
          ", lineNumber=" + lineNumber +
          '}';
    }
  }

  @Override
  public String toString() {
    return "LineNumberTableAttribute{" +
        "lineNumberTable=" + Arrays.toString(lineNumberTable) +
        '}';
  }
}