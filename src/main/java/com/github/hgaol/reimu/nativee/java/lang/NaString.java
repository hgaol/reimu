package com.github.hgaol.reimu.nativee.java.lang;

import com.github.hgaol.reimu.nativee.INativeMethod;
import com.github.hgaol.reimu.nativee.NativeMethodPool;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.heap.ReObject;
import com.github.hgaol.reimu.rtda.heap.StringPool;

/**
 * @author Gao Han
 * @date: 2018年04月27日
 */
public class NaString {

  private static final String jlString = "java/lang/String";

  public static void init() {
    NativeMethodPool.register(jlString, "intern", "()Ljava/lang/String;", intern);
  }

  // public native String intern();
  // ()Ljava/lang/String;
  // 从StringPool取出String
  private static INativeMethod intern = (Frame frame) -> {
    ReObject thisObj = frame.getLocalVars().getThis();
    ReObject jStr = StringPool.getInternString(thisObj);
    frame.getOperandStack().pushRef(jStr);
  };

}
