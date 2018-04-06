package com.github.hgaol.reimu.classfile;

import com.github.hgaol.reimu.classfile.constants.*;

import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.*;
import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.CONSTANT_CLASS;
import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.CONSTANT_DOUBLE;
import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.CONSTANT_FIELDREF;
import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.CONSTANT_FLOAT;
import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.CONSTANT_INTEGER;
import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.CONSTANT_INTERFACE_METHODREF;
import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.CONSTANT_INVOKE_DYNAMIC;
import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.CONSTANT_LONG;
import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.CONSTANT_METHODREF;
import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.CONSTANT_METHOD_HANDLE;
import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.CONSTANT_METHOD_TYPE;
import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.CONSTANT_NAMEANDTYPE;
import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.CONSTANT_STRING;
import static com.github.hgaol.reimu.classfile.constants.ConstantPoolType.CONSTANT_UTF8;
import static com.github.hgaol.reimu.util.EchoUtil.*;

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
      case CONSTANT_INTEGER:
        return new ConstantIntegerInfo();
      case CONSTANT_FLOAT:
        return new ConstantFloatInfo();
      case CONSTANT_LONG:
        return new ConstantLongInfo();
      case CONSTANT_DOUBLE:
        return new ConstantDoubleInfo();
      case CONSTANT_UTF8:
        return new ConstantUtf8Info();
      case CONSTANT_STRING:
        return new ConstantStringInfo(cp);
      case CONSTANT_CLASS:
        return new ConstantClassInfo(cp);
      case CONSTANT_FIELDREF:
        return new ConstantFieldrefInfo(cp);
      case CONSTANT_METHODREF:
        return new ConstantMethodrefInfo(cp);
      case CONSTANT_INTERFACE_METHODREF:
        return new ConstantInterfaceMethodrefInfo(cp);
      case CONSTANT_NAMEANDTYPE:
        return new ConstantNameAndTypeInfo(cp);
      case CONSTANT_METHOD_TYPE:
        return new ConstantMethodTypeInfo();
      case CONSTANT_METHOD_HANDLE:
        return new ConstantMethodHandleInfo();
      case CONSTANT_INVOKE_DYNAMIC:
        return new ConstantInvokeDynamicInfo();
      default:
        throw new RuntimeException("java.lang.ClassFormatError: constant pool tag!");
    }
  }

  public String getUtf8(int index) {
    return ((ConstantUtf8Info) constants[index]).getValue();
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
