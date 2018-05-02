package com.github.hgaol.reimu.nativee.sun.misc;

import com.github.hgaol.reimu.nativee.INativeMethod;
import com.github.hgaol.reimu.nativee.NativeMethodPool;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.ReObject;
import com.github.hgaol.reimu.rtda.heap.StringPool;
import com.github.hgaol.reimu.util.MethodUtils;

/**
 * @author Gao Han
 * @date: 2018年04月30日
 */
public class NaVM {

  private static final String smVm = "sun/misc/VM";

  public static void init() {
    NativeMethodPool.register(smVm, "initialize", "()V", initialize);
  }

  // aka: VM.savedProps.setProperty("foo", "bar")
  // 为了正确加载VM类，需要实现其中的native方法先
  private static INativeMethod initialize = (Frame frame) -> {
    ReClass vmClass = frame.getMethod().getClazz();
    ReObject savedProps = vmClass.getRefVar("savedProps", "Ljava/util/Properties;");

    ReObject foo = StringPool.getReString(vmClass.getLoader(), "foo");
    ReObject bar = StringPool.getReString(vmClass.getLoader(), "bar");

    frame.getOperandStack().pushRef(savedProps);
    frame.getOperandStack().pushRef(foo);
    frame.getOperandStack().pushRef(bar);

    ReClass propsClass = vmClass.getLoader().loadClass("java/util/Properties");
    ReClass.Method setPropMethod = propsClass.getMethod("setProperty",
        "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;", false);
    MethodUtils.invokeMethod(frame, setPropMethod);
  };

}
