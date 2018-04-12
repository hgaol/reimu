package com.github.hgaol.reimu.classfile.attribute;

import com.github.hgaol.reimu.classfile.BytesReader;
import com.github.hgaol.reimu.classfile.ConstantPool;

import java.util.Arrays;

public class CodeAttribute implements AttributeInfo {

  public ConstantPool cp;
  public int maxStack;
  public int maxLocals;
  // TODO
  public byte[] code;
  public ExceptionTableEntry[] exceptionTables;
  public AttributeInfo[] attributes;

  public CodeAttribute(ConstantPool cp) {
    this.cp = cp;
  }

  @Override
  public void readInfo(BytesReader reader) {
    this.maxStack = reader.readUnsignedShort();
    this.maxLocals = reader.readUnsignedShort();
    this.code = reader.readBytes((reader.readInt()));
    int exceptionCount = reader.readUnsignedShort();
    this.exceptionTables = new ExceptionTableEntry[exceptionCount];
    for (int i = 0; i < exceptionCount; i++) {
      this.exceptionTables[i] = new ExceptionTableEntry(reader);
    }
    this.attributes = AttributeInfoUtil.readAttributes(reader, this.cp);
  }

  // TODO
  static class ExceptionTableEntry {

    private int startPc;
    private int endPc;
    private int handlerPc;
    private int catchType;

    public ExceptionTableEntry(BytesReader reader) {
      this.startPc = reader.readUnsignedByte();
      this.endPc = reader.readUnsignedByte();
      this.handlerPc = reader.readUnsignedByte();
      this.catchType = reader.readUnsignedByte();
    }

    @Override
    public String toString() {
      return "ExceptionTableEntry{" +
          "startPc=" + startPc +
          ", endPc=" + endPc +
          ", handlerPc=" + handlerPc +
          ", catchType=" + catchType +
          '}';
    }
  }

  @Override
  public String toString() {
    return "CodeAttribute{" +
        "maxStack=" + maxStack +
        ", maxLocals=" + maxLocals +
        ", code=" + Arrays.toString(code) +
        ", exceptionTables=" + Arrays.toString(exceptionTables) +
        ", attributes=" + Arrays.toString(attributes) +
        '}';
  }
}