package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.BytesReader;
import com.github.hgaol.reimu.classfile.ConstantPool;

public class ConstantClassInfo implements ConstantInfo {

  private ConstantPool cp;
  private int index;

  public ConstantClassInfo(ConstantPool cp) {
    this.cp = cp;
  }

  @Override
  public void readInfo(BytesReader reader) {
    index = reader.readUnsignedShort();
  }

  @Override
  public String toString() {
    return "ConstantClassInfo{" +
        "index=" + index +
        '}';
  }
}