package com.github.hgaol.reimu.nativee.java.lang;

import com.github.hgaol.reimu.nativee.INativeMethod;
import com.github.hgaol.reimu.nativee.NativeMethodPool;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.LocalVars;

/**
 * @author Gao Han
 * @date: 2018年04月27日
 */
public class NaDouble {

  private static final String jlDouble = "java/lang/Double";

  public static void init() {
    NativeMethodPool.register(jlDouble, "doubleToRawLongBits", "(D)J", doubleToRawLongBits);
    NativeMethodPool.register(jlDouble, "longBitsToDouble", "(J)D", longBitsToDouble);
  }

  private static INativeMethod doubleToRawLongBits = (Frame frame) -> {
    LocalVars localVars = frame.getLocalVars();
    double value = localVars.getDouble(0);
    long result = Double.doubleToLongBits(value);
    frame.getOperandStack().pushLong(result);
  };

  private static INativeMethod longBitsToDouble = (Frame frame) -> {
    LocalVars localVars = frame.getLocalVars();
    long value = localVars.getLong(0);
    double result = Double.longBitsToDouble(value);
    frame.getOperandStack().pushDouble(result);
  };

}
