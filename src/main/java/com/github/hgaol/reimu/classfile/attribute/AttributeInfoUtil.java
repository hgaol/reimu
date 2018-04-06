package com.github.hgaol.reimu.classfile.attribute;

import com.github.hgaol.reimu.classfile.BytesReader;
import com.github.hgaol.reimu.classfile.ConstantPool;

/**
 * @author Gao Han
 * @date: 2018年04月05日
 */
public class AttributeInfoUtil {

  public static AttributeInfo[] readAttributes(BytesReader reader, ConstantPool cp) {
    int count = reader.readUnsignedShort();
    AttributeInfo[] attributes = new AttributeInfo[count];
    for (int i = 0; i < count; i++) {
      attributes[i] = readAttribute(reader, cp);
    }
    return attributes;
  }

  public static AttributeInfo readAttribute(BytesReader reader, ConstantPool cp) {
    int attrNameIndex = reader.readUnsignedShort();
    String attrName = cp.getUtf8(attrNameIndex);
    long length = reader.readUnsignedInt();
    AttributeInfo attrInfo = newAttributeInfo(attrName, length, cp);
    attrInfo.readInfo(reader);
    return attrInfo;
  }

  public static AttributeInfo newAttributeInfo(String attrName, long length, ConstantPool cp) {
    switch (attrName) {
      case "Code":
        return new CodeAttribute(cp);
      case "ConstantValue":
        return new ConstantValueAttribute();
      case "Deprecated":
        return new DeprecatedAttribute();
      case "Exceptions":
        return new ExceptionAttribute();
      case "LineNumberTable":
        return new LineNumberTableAttribute();
      case "LocalVariableTable":
        return new LocalVariableTableAttribute();
      case "SourceFile":
        return new SourceFileAttribute(cp);
      case "Synthetic":
        return new SyntheticAttribute();
      default:
        return new UnparsedAttribute(attrName, length, null);
    }
  }

}
