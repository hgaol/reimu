package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.BytesReader;
import com.github.hgaol.reimu.classfile.ConstantInfo;

public class ConstantIntegerInfo implements ConstantInfo {

  private int val;

  @Override
  public ConstantInfo readInfo(BytesReader reader) {
    return null;
  }

  public int getVal() {
    return val;
  }

}