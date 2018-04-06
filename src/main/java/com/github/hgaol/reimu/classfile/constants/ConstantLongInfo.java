package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.BytesReader;

public class ConstantLongInfo implements ConstantInfo {

  private long value;

  @Override
  public void readInfo(BytesReader reader) {
    value = reader.readLong();
  }

  public long getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "ConstantLongInfo{" +
//        "hex: " + Long.toHexString(value) +  ", value = " + value +
        "value = " + value +
        '}';
  }
}