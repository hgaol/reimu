package com.github.hgaol.reimu.classfile.attribute;

import com.github.hgaol.reimu.classfile.BytesReader;

import java.util.Arrays;

public class UnparsedAttribute implements AttributeInfo {

  private String attrName;
  private long attrLen;
  private byte[] data;

  public UnparsedAttribute(String attrName, long attrLen, byte[] data) {
    this.attrName = attrName;
    this.attrLen = attrLen;
    this.data = data;
  }

  @Override
  public void readInfo(BytesReader reader) {
    this.data = reader.readBytes((int) this.attrLen);
  }

  @Override
  public String toString() {
    return "UnparsedAttribute{" +
        "attrName='" + attrName + '\'' +
        ", attrLen=" + attrLen +
        ", data=" + Arrays.toString(data) +
        '}';
  }
}