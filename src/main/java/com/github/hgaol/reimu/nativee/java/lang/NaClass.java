package com.github.hgaol.reimu.nativee.java.lang;

import com.github.hgaol.reimu.nativee.INativeMethod;
import com.github.hgaol.reimu.nativee.NativeMethodPool;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.LocalVars;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.ReClassLoader;
import com.github.hgaol.reimu.rtda.heap.ReObject;
import com.github.hgaol.reimu.rtda.heap.StringPool;

/**
 * @author Gao Han
 * @date: 2018年04月27日
 */
public class NaClass {

  private static final String jlClass = "java/lang/Class";

  public static void init() {
    NativeMethodPool.register(jlClass, "getPrimitiveClass", "(Ljava/lang/String;)Ljava/lang/Class;", getPrimitiveClass);
    NativeMethodPool.register(jlClass, "getName0", "()Ljava/lang/String;", getName0);
    NativeMethodPool.register(jlClass, "desiredAssertionStatus0", "(Ljava/lang/Class;)Z", desiredAssertionStatus0);
  }

  // static native Class<?> getPrimitiveClass(String name);
  // (Ljava/lang/String;)Ljava/lang/Class;
  public static final INativeMethod getPrimitiveClass = (Frame frame) -> {
    ReObject nameObj = frame.getLocalVars().getRef(0);
    String name = StringPool.getOrigString(nameObj);

    ReClassLoader loader = frame.getMethod().getClazz().getLoader();
    ReObject clazz = loader.loadClass(name).getjClass();

    frame.getOperandStack().pushRef(clazz);
  };


  public static final INativeMethod getName0 = (Frame frame) -> {
    ReObject thisObj = frame.getLocalVars().getThis();
    ReClass clazz = (ReClass) thisObj.getExtra();

    String name = clazz.getJavaName();
    ReObject nameObj = StringPool.getReString(clazz.getLoader(), name);

    frame.getOperandStack().pushRef(nameObj);
  };

  public static final INativeMethod desiredAssertionStatus0 = (Frame frame) -> {
    frame.getOperandStack().pushBoolean(false);
  };

  // public native boolean isInterface();
  // ()Z
  public static final INativeMethod isInterface = (Frame frame) -> {
    LocalVars vars = frame.getLocalVars();
    ReObject thisObj = vars.getThis();
    ReClass clazz = (ReClass) thisObj.getExtra();

    frame.getOperandStack().pushBoolean(clazz.isInterface());
  };

  public static final INativeMethod isPrimitive = (Frame frame) -> {
    LocalVars vars = frame.getLocalVars();
    ReObject thisObj = vars.getThis();
    ReClass clazz = (ReClass) thisObj.getExtra();

    frame.getOperandStack().pushBoolean(clazz.isPrimitive());
  };

}
