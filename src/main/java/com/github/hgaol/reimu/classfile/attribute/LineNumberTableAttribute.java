package com.github.hgaol.reimu.classfile.attribute;

import com.github.hgaol.reimu.classfile.BytesReader;

import java.util.Arrays;

public class LineNumberTableAttribute implements AttributeInfo {

  private LineNumberInfo[] lineNumberTable;

  @Override
  public void readInfo(BytesReader reader) {
    int count = reader.readUnsignedShort();
    this.lineNumberTable = new LineNumberInfo[count];
    for (int i = 0; i < count; i++) {
      this.lineNumberTable[i] = new LineNumberInfo(reader);
    }
  }

  public int getLineNumber(int pc) {
    for (int i = this.lineNumberTable.length - 1; i >= 0; i--) {
      LineNumberInfo lni = this.lineNumberTable[i];
      if (pc >= lni.startPc) {
        return lni.lineNumber;
      }
    }
    return -1;
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