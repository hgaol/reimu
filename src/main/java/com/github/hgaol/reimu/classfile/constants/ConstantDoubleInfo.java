package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.BytesReader;

public class ConstantDoubleInfo implements ConstantInfo {

  private double value;

  @Override
  public void readInfo(BytesReader reader) {
    value = reader.readDouble();
  }

  public double getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "ConstantDoubleInfo{" +
//        "hex: " + Double.toHexString(value) +  ", value = " + value +
        "value = " + value +
        '}';
  }
}