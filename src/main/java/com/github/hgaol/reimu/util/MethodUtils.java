package com.github.hgaol.reimu.util;

import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.Thread;
import com.github.hgaol.reimu.rtda.Slot;
import com.github.hgaol.reimu.rtda.heap.ReClass;

import java.util.Arrays;
import java.util.List;

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
      if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
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
    int argSlotCount = method.getArgslotCount();
    if (argSlotCount > 0) {
      for (int i = argSlotCount - 1; i >= 0; i--) {
        Slot slot = invokerFrame.getOperandStack().popSlot();
        newFrame.getLocalVars().setSlot(i, slot);
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
      ReClass.Method[] methods = c.getMethods();
      int length = methods == null ? 0 : c.getMethods().length;
      for (int i = 0; i < length; i++) {
        if (methods[i].getName().equals(name) && methods[i].getDescriptor().equals(descriptor)) {
          return methods[i];
        }
      }
    }
    return null;
  }

  public static ReClass.Method lookupMethodInInterfaces(ReClass[] interfaces, String name, String descriptor) {
    List<ReClass> ifaces = Arrays.asList(interfaces);
    for (ReClass iface : ifaces) {
      for (ReClass.Method method : iface.getMethods()) {
        if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
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
