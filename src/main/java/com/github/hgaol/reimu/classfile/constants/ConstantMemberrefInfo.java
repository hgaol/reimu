package com.github.hgaol.reimu.classfile.constants;

import com.github.hgaol.reimu.classfile.BytesReader;
import com.github.hgaol.reimu.classfile.ConstantPool;

/**
 * @author Gao Han
 * @date: 2018年04月05日
 */
public class ConstantMemberrefInfo implements ConstantInfo {

  protected ConstantPool cp;
  protected int classIndex;
  protected int nameAndTypeIndex;

  public String getClassName() {
    return cp.getUtf8(classIndex);
  }

  public String getName() {
    ConstantNameAndTypeInfo ntInfo = (ConstantNameAndTypeInfo)cp.constants[nameAndTypeIndex];
    return cp.getUtf8(ntInfo.getNameIndex());
  }

  public String getDescriptor() {
    ConstantNameAndTypeInfo ntInfo = (ConstantNameAndTypeInfo)cp.constants[nameAndTypeIndex];
    return cp.getUtf8(ntInfo.getDescriptorIndex());
  }

  public ConstantMemberrefInfo(ConstantPool cp) {
    this.cp = cp;
  }

  @Override
  public void readInfo(BytesReader reader) {
    classIndex = reader.readUnsignedShort();
    nameAndTypeIndex = reader.readUnsignedShort();
  }
}
