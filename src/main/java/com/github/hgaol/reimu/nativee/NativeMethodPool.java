package com.github.hgaol.reimu.nativee;

import com.github.hgaol.reimu.nativee.java.lang.*;
import com.github.hgaol.reimu.nativee.sun.misc.NaVM;
import com.github.hgaol.reimu.rtda.Frame;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地方法池
 * @author Gao Han
 * @date: 2018年04月27日
 */
public class NativeMethodPool {

  private static Map<String, INativeMethod> registry = new HashMap<>();

  static {
    NaObject.init();
    NaClass.init();
    NaFloat.init();
    NaDouble.init();
    NaString.init();
    NaSystem.init();
    NaVM.init();
    NaThrowable.init();
  }

  private static final INativeMethod emptyMethod = (Frame frame) -> { };

  public static void register(String className, String methodName, String methodDescriptor, INativeMethod method) {
    String key = className + "~" + methodDescriptor + "~" + methodDescriptor;
    registry.put(key, method);
  }

  public static INativeMethod findNativeMethod(String className, String methodName, String methodDescriptor) {
    String key = className + "~" + methodDescriptor + "~" + methodDescriptor;
    INativeMethod method = registry.get(key);
    if (method != null) {
      return method;
    }
    if (methodDescriptor.equals("()V") && methodName.equals("registerNatives")) {
      return emptyMethod;
    }
    return null;
  }

}

