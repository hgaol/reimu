package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.BytesReader;

public class ConstantFloatInfo implements ConstantInfo {

  private float value;

  public float getValue() {
    return value;
  }

  @Override
  public void readInfo(BytesReader reader) {
    value = reader.readFloat();
  }

  @Override
  public String toString() {
    return "ConstantFloatInfo{" +
        "value=" + value +
        '}';
  }
}