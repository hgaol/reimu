package com.github.hgaol.reimu.nativee.java.lang;

import com.github.hgaol.reimu.nativee.INativeMethod;
import com.github.hgaol.reimu.nativee.NativeMethodPool;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.LocalVars;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.ReObject;

/**
 * @author Gao Han
 * @date: 2018年04月27日
 */
public class NaSystem {

  private static final String jlSystem = "java/lang/System";

  public static void init() {
    NativeMethodPool.register(jlSystem, "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", arraycopy);
  }

  // public static native void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
  // (Ljava/lang/Object;ILjava/lang/Object;II)V
  public static final INativeMethod arraycopy = (Frame frame) -> {
    LocalVars localVars = frame.getLocalVars();
    ReObject src = localVars.getRef(0);
    int srcPos = localVars.getInt(1);
    ReObject desc = localVars.getRef(2);
    int descPos = localVars.getInt(3);
    int length = localVars.getInt(4);
    if (src == null || desc == null) {
      throw new Error("java.lang.NullPointerException");
    }
    if (!checkArrayCopy(src, desc)) {
      throw new Error("java.lang.ArrayStoreException");
    }
    System.arraycopy(src.getArrayData(), srcPos, desc.getArrayData(), descPos, length);
  };

  private static boolean checkArrayCopy(ReObject src, ReObject desc) {
    ReClass srcClass = src.getClazz();
    ReClass descClass = desc.getClazz();

    if (!srcClass.isArray() || !descClass.isArray()) {
      return false;
    }

    if (srcClass.componentClass().isPrimitive() ||
        descClass.componentClass().isPrimitive()) {
      return srcClass == descClass;
    }

    return true;
  }

}
