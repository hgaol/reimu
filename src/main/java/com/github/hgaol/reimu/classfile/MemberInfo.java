package com.github.hgaol.reimu.classfile;

import com.github.hgaol.reimu.classfile.attribute.AttributeInfoUtil;
import com.github.hgaol.reimu.classfile.attribute.AttributeInfo;

/**
 * 传入常量池，为了可以根据index获取value
 *
 * @author Gao Han
 * @date: 2018年04月04日
 */
public class MemberInfo {

  private ConstantPool cp;
  private int accessFlags;
  private int nameIndex;
  private int descriptorIndex;
  private AttributeInfo[] attributes;

  public MemberInfo(ConstantPool cp, int accessFlags, int nameIndex, int descriptorIndex, AttributeInfo[] attributes) {
    this.cp = cp;
    this.accessFlags = accessFlags;
    this.nameIndex = nameIndex;
    this.descriptorIndex = descriptorIndex;
    this.attributes = attributes;
  }

  public static MemberInfo readMemberInfo(BytesReader reader, ConstantPool cp) {
    // TODO
    return new MemberInfo(
        cp,
        reader.readUnsignedShort(),
        reader.readUnsignedShort(),
        reader.readUnsignedShort(),
        AttributeInfoUtil.readAttributes(reader, cp));
  }

}
