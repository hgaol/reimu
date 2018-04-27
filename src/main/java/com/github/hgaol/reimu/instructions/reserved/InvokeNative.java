package com.github.hgaol.reimu.instructions.reserved;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.nativee.INativeMethod;
import com.github.hgaol.reimu.nativee.NativeMethodPool;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.heap.ReClass;

/**
 * @author Gao Han
 * @date: 2018年04月27日
 */
public class InvokeNative extends NoOperandsInstruction {

  @Override
  public void execute(Frame frame) {
    ReClass.Method method = frame.getMethod();
    String className = method.getClazz().getName();
    String methodName = method.getName();
    String methodDescriptor = method.getDescriptor();

    // 寻找本地方法
    INativeMethod nativeMethod = NativeMethodPool.findNativeMethod(className, methodName, methodDescriptor);
    if (nativeMethod == null) {
      String methodInfo = className + "." + methodName + methodDescriptor;
      throw new Error("java.lang.UnsatisfiedLinkError: " + methodInfo);
    }

    // 执行本地方法
    nativeMethod.invoke(frame);
  }

}
