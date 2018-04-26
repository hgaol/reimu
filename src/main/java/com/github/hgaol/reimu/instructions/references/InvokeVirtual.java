package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.Index16Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.heap.*;

/**
 * 需要动态绑定的方法，针对非接口类型的调用方法
 * Invoke instance method; dispatch based on class
 * todo: ? invoke_virtual和invoke_special的区别，如何体现调用object的方法(字节码中何时如何访问ReObject.fields)
 * A: 应该是在新的frame中的操作数中进行操作object相关内容
 *
 * @author Gao Han
 * @date: 2018年04月24日
 */
public class InvokeVirtual extends Index16Instruction {
  @Override
  public void execute(Frame frame) {
    ReClass curClass = frame.getMethod().getClazz();
    RtConstantPool cp = frame.getMethod().getClazz().getConstantPool();
    CpInfos.MethodRef methodRef = (CpInfos.MethodRef) cp.getConstant(index);
    ReClass.Method resolvedMethod = methodRef.resolvedMethod();

    if (resolvedMethod.isStatic()) {
      throw new Error("java.lang.IncompatibleClassChangeError");
    }

    ReObject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgslotCount() - 1);
    if (ref == null) {
      // todo: hack
      if (methodRef.getName().equals("println")) {
        _println(frame.getOperandStack(), methodRef.getDescriptor());
        return;
      }
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

    // 区别于invoke_special的地方
    ReClass.Method methodToBeInvoked = MethodUtils.lookupMethodInClass(ref.getClazz(),
        methodRef.getName(), methodRef.getDescriptor());
    if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
      throw new Error("java.lang.AbstractMethodError");
    }

    MethodUtils.invokeMethod(frame, methodToBeInvoked);
  }

  private void _println(OperandStack stack, String descriptor) {
    switch (descriptor) {
      case "(Z)V":
        System.out.printf("%s\n", stack.popInt() != 0);
        break;
      case "(C)V":
        System.out.printf("%d\n", stack.popInt());
        break;
      case "(I)V":
      case "(B)V":
      case "(S)V":
        System.out.printf("%d\n", stack.popInt());
        break;
      case "(F)V":
        System.out.printf("%f\n", stack.popFloat());
        break;
      case "(J)V":
        System.out.printf("%d\n", stack.popLong());
        break;
      case "(D)V":
        System.out.printf("%f\n", stack.popDouble());
        break;
      default:
        throw new Error("println: " + descriptor);
    }
    stack.popRef();
  }

}
