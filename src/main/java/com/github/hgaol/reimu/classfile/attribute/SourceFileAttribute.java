package com.github.hgaol.reimu.classfile.attribute;

import com.github.hgaol.reimu.classfile.BytesReader;
import com.github.hgaol.reimu.classfile.ConstantPool;

/**
 * sourceFileIndex指向常量值索引，表示该类对应的文件名
 */
public class SourceFileAttribute implements AttributeInfo{

  private ConstantPool cp;
  private int sourceFileIndex;

  public SourceFileAttribute(ConstantPool cp) {
    this.cp = cp;
  }

  @Override
  public void readInfo(BytesReader reader) {
    this.sourceFileIndex = reader.readUnsignedShort();
  }

  @Override
  public String toString() {
    return "SourceFileAttribute{" +
        "sourceFileIndex=" + sourceFileIndex +
        '}';
  }
}