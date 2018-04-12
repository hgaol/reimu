package com.github.hgaol.reimu.classfile;

import com.github.hgaol.reimu.classfile.attribute.AttributeInfo;
import com.github.hgaol.reimu.classfile.attribute.AttributeInfoUtil;
import com.github.hgaol.reimu.classfile.attribute.CodeAttribute;

import java.util.Arrays;

/**
 * 传入常量池，为了可以根据index获取value
 *
 * @author Gao Han
 * @date: 2018年04月04日
 */
public class MemberInfo {

  public ConstantPool cp;
  public int accessFlags;
  public int nameIndex;
  public int descriptorIndex;
  public AttributeInfo[] attributes;

  public MemberInfo(ConstantPool cp, int accessFlags, int nameIndex, int descriptorIndex, AttributeInfo[] attributes) {
    this.cp = cp;
    this.accessFlags = accessFlags;
    this.nameIndex = nameIndex;
    this.descriptorIndex = descriptorIndex;
    this.attributes = attributes;
  }

  public CodeAttribute getCodeAttribute() {
    for (AttributeInfo attrInfo : attributes) {
      if (attrInfo instanceof CodeAttribute) {
        return (CodeAttribute) attrInfo;
      }
    }
    return null;
  }

  public String getName() {
    return this.cp.getUtf8(this.nameIndex);
  }

  public String getDescriptor() {
    return this.cp.getUtf8(this.descriptorIndex);
  }

  public static MemberInfo readMemberInfo(BytesReader reader, ConstantPool cp) {
    return new MemberInfo(
        cp,
        reader.readUnsignedShort(),
        reader.readUnsignedShort(),
        reader.readUnsignedShort(),
        AttributeInfoUtil.readAttributes(reader, cp));
  }

  @Override
  public String toString() {
    return "MemberInfo{" +
        "accessFlags=" + accessFlags +
        ", nameIndex=" + nameIndex +
        ", descriptorIndex=" + descriptorIndex +
        ", attributes=" + Arrays.toString(attributes) +
        '}';
  }
}
