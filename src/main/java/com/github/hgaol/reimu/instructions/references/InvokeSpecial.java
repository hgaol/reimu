package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.Index16Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.heap.*;

/**
 * 用来调用无需动态绑定的实例方法
 * Invoke instance method
 * special handling for superclass, private, and instance initialization method invocations
 *
 * @author Gao Han
 * @date: 2018年04月24日
 */
public class InvokeSpecial extends Index16Instruction {
  @Override
  public void execute(Frame frame) {
    ReClass curClass = frame.getMethod().getClazz();
    RtConstantPool cp = curClass.getConstantPool();
    CpInfos.MethodRef methodRef = (CpInfos.MethodRef) cp.getConstant(index);
    ReClass resolvedClass = methodRef.resolvedClass();
    ReClass.Method resolvedMethod = methodRef.resolvedMethod();

    /** 可访问性 */
    if ("<init>".equals(resolvedMethod.getName()) && resolvedMethod.getClazz() != resolvedClass) {
      throw new Error("java.lang.NoSuchMethodError");
    }
    if (resolvedMethod.isStatic()) {
      throw new Error("java.lang.IncompatibleClassChangeError");
    }

    // get `this` object
    ReObject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgslotCount() - 1);
    if (ref == null) {
      throw new Error("java.lang.NullPointerException");
    }

    // protected方法只能被该类or子类调用
    if (resolvedMethod.isProtected() &&
        resolvedMethod.getClazz().isSubClassOf(curClass) &&
        !resolvedMethod.getClazz().getPackageName().equals(curClass.getPackageName()) &&
        ref.getClazz() != curClass &&
        !ref.getClazz().isSubClassOf(curClass)) {
      throw new Error("java.lang.IllegalAccessError");
    }

    ReClass.Method methodToBeInvoked = resolvedMethod;
    // todo: ? 如果调用的是超类中的函数且不是构造函数，需要查找最终的调用方法
    if (curClass.isSuper() &&
        resolvedClass.isSuperClassOf(curClass) &&
        !resolvedClass.getName().equals("<init>")) {
      methodToBeInvoked = MethodUtils.lookupMethodInClass(curClass.getSuperClass(), methodRef.getName(), methodRef.getDescriptor());
    }

    if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
      throw new Error("java.lang.AbstractMethodError");
    }

    MethodUtils.invokeMethod(frame, methodToBeInvoked);
  }

}

