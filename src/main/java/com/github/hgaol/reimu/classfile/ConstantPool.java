package com.github.hgaol.reimu.classfile;

import com.github.hgaol.reimu.classfile.constants.*;

import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.*;

/**
 * @author Gao Han
 * @date: 2018年04月04日
 */
public class ConstantPool {

  public ConstantInfo[] constants;

  public static ConstantPool readConstantPool(BytesReader reader) {
    ConstantPool cp = new ConstantPool();
    int count = reader.readUnsignedShort();
    cp.constants = new ConstantInfo[count];
    for (int i = 1; i < count; i++) {
      cp.constants[i] = readConstantInfo(reader, cp);
      cp.constants[i].readInfo(reader);
      if (cp.constants[i] instanceof ConstantLongInfo ||
          cp.constants[i] instanceof ConstantDoubleInfo) {
        i++;
      }
    }
    return cp;
  }

  private static ConstantInfo readConstantInfo(BytesReader reader, ConstantPool cp) {
    short tag = reader.readByte();
    switch (tag) {
      case ConstantPoolType.CONSTANT_INTEGER:
        return new ConstantIntegerInfo();
      case ConstantPoolType.CONSTANT_FLOAT:
        return new ConstantFloatInfo();
      case ConstantPoolType.CONSTANT_LONG:
        return new ConstantLongInfo();
      case ConstantPoolType.CONSTANT_DOUBLE:
        return new ConstantDoubleInfo();
      case ConstantPoolType.CONSTANT_UTF8:
        return new ConstantUtf8Info();
      case ConstantPoolType.CONSTANT_STRING:
        return new ConstantStringInfo(cp);
      case ConstantPoolType.CONSTANT_CLASS:
        return new ConstantClassInfo(cp);
      case ConstantPoolType.CONSTANT_FIELDREF:
        return new ConstantFieldrefInfo(cp);
      case ConstantPoolType.CONSTANT_METHODREF:
        return new ConstantMethodrefInfo(cp);
      case ConstantPoolType.CONSTANT_INTERFACE_METHODREF:
        return new ConstantInterfaceMethodrefInfo(cp);
      case ConstantPoolType.CONSTANT_NAMEANDTYPE:
        return new ConstantNameAndTypeInfo(cp);
      case ConstantPoolType.CONSTANT_METHOD_TYPE:
        return new ConstantMethodTypeInfo();
      case ConstantPoolType.CONSTANT_METHOD_HANDLE:
        return new ConstantMethodHandleInfo();
      case ConstantPoolType.CONSTANT_INVOKE_DYNAMIC:
        return new ConstantInvokeDynamicInfo();
      default:
        throw new RuntimeException("java.lang.ClassFormatError: constant pool tag!");
    }
  }

  public String getUtf8(int index) {
    return ((ConstantUtf8Info) constants[index]).getValue();
  }

  public String getClassName(int index) {
    return getUtf8(((ConstantClassInfo) constants[index]).index);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ConstantPool:\n");
    for (int i = 1; i < constants.length; i++) {
      sb.append("#" + i + ": ").append(constants[i]).append("\n");
      if (constants[i] instanceof ConstantLongInfo ||
          constants[i] instanceof ConstantDoubleInfo) {
        i++;
      }
    }
    return sb.toString();
  }
}
