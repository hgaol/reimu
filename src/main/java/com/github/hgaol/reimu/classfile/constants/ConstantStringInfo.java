package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.BytesReader;
import com.github.hgaol.reimu.classfile.ConstantPool;

public class ConstantStringInfo implements ConstantInfo {

  private ConstantPool cp;
  private int index;

  public ConstantStringInfo(ConstantPool cp) {
    this.cp = cp;
  }

  public String getValue() {
    return cp.getUtf8(index);
  }

  @Override
  public void readInfo(BytesReader reader) {
    index = reader.readUnsignedShort();
  }

  @Override
  public String toString() {
    return "ConstantStringInfo{" +
        "index=" + index +
        '}';
  }
}