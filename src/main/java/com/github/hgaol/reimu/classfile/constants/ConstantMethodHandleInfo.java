package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.BytesReader;

public class ConstantMethodHandleInfo implements ConstantInfo {

  // 值在1-9之间，决定方法句柄的类型
  private short refKind;
  // 常量池索引
  private int refIndex;

  @Override
  public void readInfo(BytesReader reader) {
    refKind = reader.readByte();
    refIndex = reader.readUnsignedShort();
  }

  @Override
  public String toString() {
    return "ConstantMethodHandleInfo{" +
        "refKind=" + refKind +
        ", refIndex=" + refIndex +
        '}';
  }
}