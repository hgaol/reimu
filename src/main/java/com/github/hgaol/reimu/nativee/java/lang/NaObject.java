package com.github.hgaol.reimu.nativee.java.lang;

import com.github.hgaol.reimu.nativee.INativeMethod;
import com.github.hgaol.reimu.nativee.NativeMethodPool;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.heap.ReObject;

/**
 * @author Gao Han
 * @date: 2018年04月27日
 */
public class NaObject {

  private static final String jlObject = "java/lang/Object";

  public static void init() {
    NativeMethodPool.register(jlObject, "getClass", "()Ljava/lang/Class;", getClass);
  }

  // public final native Class<?> getClass();
  // ()Ljava/lang/Class;
  public static final INativeMethod getClass = (Frame frame) -> {
    ReObject thisObj = frame.getLocalVars().getThis();
    ReObject classObj = thisObj.getClazz().getjClass();
    frame.getOperandStack().pushRef(classObj);
  };

}
