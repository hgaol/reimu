package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.BytesReader;

public class ConstantIntegerInfo implements ConstantInfo {

  private int value;

  @Override
  public void readInfo(BytesReader reader) {
    value = reader.readInt();
  }

  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "ConstantIntegerInfo{" +
        "value=" + value +
        '}';
  }
}