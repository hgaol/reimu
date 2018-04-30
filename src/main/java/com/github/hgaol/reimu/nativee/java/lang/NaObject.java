package com.github.hgaol.reimu.nativee.java.lang;

import com.github.hgaol.reimu.nativee.INativeMethod;
import com.github.hgaol.reimu.nativee.NativeMethodPool;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.ReObject;

/**
 * @author Gao Han
 * @date: 2018年04月27日
 */
public class NaObject {

  private static final String jlObject = "java/lang/Object";

  public static void init() {
    NativeMethodPool.register(jlObject, "getClass", "()Ljava/lang/Class;", getClass);
    NativeMethodPool.register(jlObject, "hashCode", "()I", hashCode);
    NativeMethodPool.register(jlObject, "clone", "()Ljava/lang/Object;", clone);
  }

  // public final native Class<?> getClass();
  // ()Ljava/lang/Class;
  public static final INativeMethod getClass = (Frame frame) -> {
    ReObject thisObj = frame.getLocalVars().getThis();
    ReObject classObj = thisObj.getClazz().getjClass();
    frame.getOperandStack().pushRef(classObj);
  };

  // public native int hashCode();
  // ()I
  public static final INativeMethod hashCode = (Frame frame) -> {
    ReObject thisObj = frame.getLocalVars().getThis();
    int hashCode = thisObj.hashCode();
    frame.getOperandStack().pushInt(hashCode);
  };

  public static final INativeMethod clone = (Frame frame) -> {
    ReObject thisObj = frame.getLocalVars().getThis();

    ReClass cloneable = thisObj.getClazz().getLoader().loadClass("java/lang/Cloneable");
    if (!thisObj.getClazz().isImplements(cloneable)) {
      throw new Error("java.lang.CloneNotSupportedException");
    }

    frame.getOperandStack().pushRef(thisObj.reClone());
  };

}
