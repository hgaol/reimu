package com.github.hgaol.reimu.rtda.heap;

import com.github.hgaol.reimu.classfile.ConstantPool;
import com.github.hgaol.reimu.classfile.constants.*;

/**
 * @author Gao Han
 * @date: 2018年04月22日
 */
public class RtConstantPool {
  private Class clazz;
  private Object[] constant;

  public RtConstantPool(Class clazz, ConstantPool cfCp) {
    this.clazz = clazz;
    constant = new Object[cfCp.constants.length];

    for (int i = 0; i < cfCp.constants.length; i++) {
      ConstantInfo cfInfo = cfCp.constants[i];
      ConstantType constantType = ConstantType.valueOf(cfInfo.getClass().getSimpleName());
      switch (constantType) {
        case ConstantIntegerInfo:
          constant[i] = ((ConstantIntegerInfo) cfInfo).getValue();
        case ConstantFloatInfo:
          constant[i] = ((ConstantFloatInfo) cfInfo).getValue();
        case ConstantLongInfo:
          constant[i] = ((ConstantLongInfo) cfInfo).getValue();
          i++;
        case ConstantDoubleInfo:
          constant[i] = ((ConstantDoubleInfo) cfInfo).getValue();
          i++;
        case ConstantStringInfo:
          constant[i] = ((ConstantStringInfo) cfInfo).getValue();
        case ConstantClassInfo:
          constant[i] = new CpInfos.ClassRef(this, (ConstantClassInfo) cfInfo);
        case ConstantFieldrefInfo:
          constant[i] = new CpInfos.FieldRef(this, (ConstantFieldrefInfo) cfInfo);
        case ConstantMethodrefInfo:
          constant[i] = new CpInfos.MethodRef(this, (ConstantMethodrefInfo) cfInfo);
        case ConstantInterfacemethodrefInfo:
          constant[i] = new CpInfos.InterfaceMethodRef(this, (ConstantInterfaceMethodrefInfo) cfInfo);
        default:
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

  public Class getClazz() {
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
    ConstantInterfacemethodrefInfo
  }
}
