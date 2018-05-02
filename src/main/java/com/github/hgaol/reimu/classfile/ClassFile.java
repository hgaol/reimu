package com.github.hgaol.reimu.classfile;

import com.github.hgaol.reimu.classfile.attribute.*;
import com.github.hgaol.reimu.classfile.attribute.AttributeInfo;
import com.github.hgaol.reimu.classfile.attribute.AttributeInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static com.github.hgaol.reimu.util.EchoUtils.*;

/**
 * @author Gao Han
 * @date: 2018年04月04日
 */
public class ClassFile {

  private static final Logger logger = LoggerFactory.getLogger(ClassFile.class);

  // u4
  public int magic;
  // u2
  public int minorVersion;
  // u2
  public int majorVersion;
  //
  public ConstantPool constantPool;
  // u2
  public int accessFlags;
  // u2
  public int thisClass;
  // u2
  public int superClass;
  // u2
  public int[] interfaces;
  public MemberInfo[] fields;
  public MemberInfo[] methods;
  public AttributeInfo[] attributes;

  public void read(BytesReader reader) {
    // TODO
    readAndCheckMagic(reader);
    readAndCheckVersion(reader);
    this.constantPool = ConstantPool.readConstantPool(reader);
    this.accessFlags = reader.readUnsignedShort();
    this.thisClass = reader.readUnsignedShort();
    this.superClass = reader.readUnsignedShort();
    this.interfaces = new int[reader.readUnsignedShort()];
    for (int i = 0; i < interfaces.length; i++) {
      this.interfaces[i] = reader.readUnsignedShort();
    }
    this.fields = readMembers(reader);
    this.methods = readMembers(reader);
    this.attributes = readAttributes(reader);
  }

  public MemberInfo[] getFields() {
    return fields;
  }

  public MemberInfo[] getMethods() {
    return methods;
  }

  public AttributeInfo[] getAttributes() {
    return attributes;
  }

  public String getClassName() {
    return this.constantPool.getClassName(thisClass);
  }

  public String[] getInterfaceNames() {
    String[] interfaceNames = new String[interfaces.length];
    for (int i = 0; i < interfaces.length; i++) {
      interfaceNames[i] = this.constantPool.getClassName(interfaces[i]);
    }
    return interfaceNames;
  }

  public String getSuperClassName() {
    if (superClass > 0) {
      return this.constantPool.getClassName(superClass);
    } else {
      return "";
    }
  }

  private void readAndCheckMagic(BytesReader reader) {
    magic = reader.readInt();
    logger.debug("magic number: 0x{}", Integer.toHexString(magic));
    if (magic != 0xCAFEBABE) {
      throw new Error("wrong magic number: " + magic);
    }
  }

  private void readAndCheckVersion(BytesReader reader) {
    minorVersion = reader.readUnsignedShort();
    majorVersion = reader.readUnsignedShort();

    logger.debug("minorVersion: " + minorVersion);
    logger.debug("majorVersion: " + majorVersion);

    switch (majorVersion) {
      case 45:
        return;
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
        if (minorVersion == 0) {
          return;
        }
      default:
        throw new RuntimeException("java.lang.UnsupportedClassVersionError!");
    }
  }

  private MemberInfo[] readMembers(BytesReader reader) {
    int count = reader.readUnsignedShort();
    MemberInfo[] members = new MemberInfo[count];
    for (int i = 0; i < count; i++) {
      members[i] = MemberInfo.readMemberInfo(reader, this.constantPool);
    }
    return members;
  }

  private AttributeInfo[] readAttributes(BytesReader reader) {
    return AttributeInfoUtil.readAttributes(reader, this.constantPool);
  }

  public SourceFileAttribute getSourceFileAttribute() {
    for (AttributeInfo attrInfo : this.attributes) {
      if (attrInfo instanceof SourceFileAttribute) {
        return (SourceFileAttribute) attrInfo;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return "ClassFile{\n" +
        "magic=" + magic +
        "\n, minorVersion=" + minorVersion +
        "\n, majorVersion=" + majorVersion +
        "\n, constantPool=" + constantPool +
        "\n, accessFlags=" + accessFlags +
        "\n, thisClass=" + thisClass +
        "\n, superClass=" + superClass +
        "\n, interfaces=" + Arrays.toString(interfaces) +
        "\n, fields=" + Arrays.toString(fields) +
        "\n, methods=" + Arrays.toString(methods) +
        "\n, attributes=" + Arrays.toString(attributes) +
        "\n}";
  }
}
