package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.BytesReader;

public class ConstantUtf8Info implements ConstantInfo {

  private String value;

  public String getValue() {
    return value;
  }

  @Override
  public void readInfo(BytesReader reader) {
    int count = reader.readUnsignedShort();
    value = new String(reader.readBytes(count));
  }

  @Override
  public String toString() {
    return "ConstantUtf8Info{" +
        "value='" + value + '\'' +
        '}';
  }
}