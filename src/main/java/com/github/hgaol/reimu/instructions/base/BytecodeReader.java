package com.github.hgaol.reimu.instructions.base;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public class BytecodeReader {
  private int pc;
  private ByteBuffer code;

  public BytecodeReader(byte[] code) {
    this.code = ByteBuffer.wrap(code);
  }

  public void reset(byte[] code, int pc, ByteOrder order) {
    this.code = ByteBuffer.wrap(code);
    this.pc = pc;
  }

  public int getPc() {
    return pc;
  }

  public void setPc(int pc) {
    this.pc = pc;
  }

  public byte getByte() {
    byte data = code.get(pc);
    pc++;
    return data;
  }

  public char getChar() {
    char data = code.getChar(pc);
    pc += 2;
    return data;
  }

  public short getShort() {
    short data = code.getShort(pc);
    pc += 2;
    return data;
  }

  public int getUnsignedShort() {
    return getShort() & 0x0000ffff;
  }

  public int getInt() {
    int data = code.getInt(pc);
    pc += 4;
    return data;
  }

  public int[] getInts(int length) {
    int[] ret = new int[length];
    for (int i = 0; i < length; i++) {
      ret[i] = getInt();
    }
    return ret;
  }

  public long getLong() {
    long data = code.getLong(pc);
    pc += 8;
    return data;
  }

  public float getFloat() {
    float data = code.getFloat(pc);
    pc += 4;
    return data;
  }

  public double getDouble() {
    double data = code.getDouble(pc);
    pc += 8;
    return data;
  }

  public void skipPadding() {
    while (this.pc % 4 != 0) {
      getByte();
    }
  }
}
