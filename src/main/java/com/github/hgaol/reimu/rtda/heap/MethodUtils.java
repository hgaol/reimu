package com.github.hgaol.reimu.rtda.heap;

import com.github.hgaol.reimu.rtda.Thread;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.Slot;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Gao Han
 * @date: 2018年04月25日
 */
public class MethodUtils {

  public static ReClass.Method lookupMethod(ReClass clazz, String name, String descriptor) {
    ReClass.Method method = lookupMethodInClass(clazz, name, descriptor);
    if (method == null) {
      method = lookupMethodInInterfaces(clazz.getInterfaces(), name, descriptor);
    }
    return method;
  }

  public static ReClass.Method lookupInterfaceMethod(ReClass iface, String name, String descriptor) {
    for (ReClass.Method method : iface.getMethods()) {
      if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
        return method;
      }
    }
    return lookupMethodInInterfaces(iface.getInterfaces(), name, descriptor);
  }

  public static ReClass.Method.MethodDescriptor parseMethodDescriptor(String descriptor) {
    ReClass.Method.MethodDescriptorParser parser = new ReClass.Method.MethodDescriptorParser();
    return parser.parse(descriptor);
  }

  /**
   * 调用方法：
   * 1. 用被调用的method构建新的frame，push到thread中
   * 2. copy方法的参数（旧frame的操作数栈 -> 新frame的本地变量表
   * 3. 下次loop就会调用新的方法了
   *
   * @param invokerFrame 当前frame
   * @param method       被调用的method
   */
  public static void invokeMethod(Frame invokerFrame, ReClass.Method method) {
    // 1. 用被调用的method构建新的frame，push到thread中
    Thread thread = invokerFrame.getThread();
    Frame newFrame = thread.newFrame(method);
    thread.pushFrame(newFrame);

    // 2. copy方法的参数（旧frame的操作数栈 -> 新frame的本地变量表
    int argSlotCount = method.argslotCount;
    if (argSlotCount > 0) {
      for (int i = argSlotCount - 1; i >= 0; i--) {
        Slot slot = invokerFrame.getOperandStack().popSlot();
        newFrame.getLocalVars().setSlot(i, slot);
      }
    }

    // todo: hack!
    if (method.isNative()) {
      if (method.name.equals("registerNatives")) {
        thread.popFrame();
      } else {
        // native method 先不处理
        throw new Error(String.format("native method: %s.%s%s\n",
            ToStringBuilder.reflectionToString(method.clazz), method.name, method.descriptor));
      }
    }
  }

  /**
   * 从当前类开始，遍历父类找指定的method
   *
   * @param clazz      class
   * @param name       method name
   * @param descriptor method descriptor
   * @return
   */
  public static ReClass.Method lookupMethodInClass(ReClass clazz, String name, String descriptor) {
    for (ReClass c = clazz; c != null; c = c.getSuperClass()) {
      for (ReClass.Method method : c.getMethods()) {
        if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
          return method;
        }
      }
    }
    return null;
  }

  public static ReClass.Method lookupMethodInInterfaces(ReClass[] interfaces, String name, String descriptor) {

    for (ReClass iface : interfaces) {
      for (ReClass.Method method : iface.getMethods()) {
        if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
          return method;
        }
      }

      ReClass.Method method = lookupMethodInInterfaces(iface.getInterfaces(), name, descriptor);
      if (method != null) {
        return method;
      }
    }

    return null;
  }
}
