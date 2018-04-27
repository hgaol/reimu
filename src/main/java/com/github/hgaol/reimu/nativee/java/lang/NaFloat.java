package com.github.hgaol.reimu.nativee.java.lang;

import com.github.hgaol.reimu.nativee.INativeMethod;
import com.github.hgaol.reimu.nativee.NativeMethodPool;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.LocalVars;

/**
 * @author Gao Han
 * @date: 2018年04月27日
 */
public class NaFloat {

  private static final String jlFloat = "java/lang/Float";

  public static void init() {
    NativeMethodPool.register(jlFloat, "floatToRawIntBits", "(F)I", floatToRawIntBits);
    NativeMethodPool.register(jlFloat, "intBitsToFloat", "(I)F", intBitsToFloat);
  }

  private static INativeMethod floatToRawIntBits = (Frame frame) -> {
    LocalVars localVars = frame.getLocalVars();
    float value = localVars.getFloat(0);
    int result = Float.floatToRawIntBits(value);
    frame.getOperandStack().pushInt(result);
  };

  private static INativeMethod intBitsToFloat = (Frame frame) -> {
    LocalVars localVars = frame.getLocalVars();
    int value = localVars.getInt(0);
    float result = Float.intBitsToFloat(value);
    frame.getOperandStack().pushFloat(result);
  };

}
