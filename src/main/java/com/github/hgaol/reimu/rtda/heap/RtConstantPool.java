package com.github.hgaol.reimu.rtda.heap;

import com.github.hgaol.reimu.classfile.ConstantPool;
import com.github.hgaol.reimu.classfile.constants.*;

/**
 * @author Gao Han
 * @date: 2018年04月22日
 */
public class RtConstantPool {
  private ReClass clazz;
  private Object[] constant;

  public RtConstantPool(ReClass clazz, ConstantPool cfCp) {
    this.clazz = clazz;
    constant = new Object[cfCp.constants.length];

    for (int i = 1; i < cfCp.constants.length; i++) {
      ConstantInfo cfInfo = cfCp.constants[i];
      ConstantType constantType = ConstantType.valueOf(cfInfo.getClass().getSimpleName());
      switch (constantType) {
        case ConstantIntegerInfo:
          constant[i] = ((ConstantIntegerInfo) cfInfo).getValue();
          break;
        case ConstantFloatInfo:
          constant[i] = ((ConstantFloatInfo) cfInfo).getValue();
          break;
        case ConstantLongInfo:
          constant[i] = ((ConstantLongInfo) cfInfo).getValue();
          i++;
          break;
        case ConstantDoubleInfo:
          constant[i] = ((ConstantDoubleInfo) cfInfo).getValue();
          i++;
          break;
        case ConstantStringInfo:
          constant[i] = ((ConstantStringInfo) cfInfo).getValue();
          break;
        case ConstantClassInfo:
          constant[i] = new CpInfos.ClassRef(this, (ConstantClassInfo) cfInfo);
          break;
        case ConstantFieldrefInfo:
          constant[i] = new CpInfos.FieldRef(this, (ConstantFieldrefInfo) cfInfo);
          break;
        case ConstantMethodrefInfo:
          constant[i] = new CpInfos.MethodRef(this, (ConstantMethodrefInfo) cfInfo);
          break;
        case ConstantInterfaceMethodrefInfo:
          constant[i] = new CpInfos.InterfaceMethodRef(this, (ConstantInterfaceMethodrefInfo) cfInfo);
          break;
        default:
          // todo
      }
    }

  }

  public Object getConstant(int index) {
    Object val = this.constant[index];
    if (val == null) {
      throw new Error("No constants at index " + index);
    }
    return val;
  }

  public ReClass getClazz() {
    return clazz;
  }

  enum ConstantType {
    ConstantIntegerInfo,
    ConstantFloatInfo,
    ConstantLongInfo,
    ConstantDoubleInfo,
    ConstantStringInfo,
    ConstantClassInfo,
    ConstantFieldrefInfo,
    ConstantMethodrefInfo,
    ConstantUtf8Info,
    ConstantNameAndTypeInfo,
    ConstantInterfaceMethodrefInfo,
    ConstantInvokeDynamicInfo,
    ConstantMethodHandleInfo,
    ConstantMethodTypeInfo
  }
}
