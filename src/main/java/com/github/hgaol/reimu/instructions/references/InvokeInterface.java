package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.BytecodeReader;
import com.github.hgaol.reimu.instructions.base.Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.heap.*;

/**
 * 操作码后面跟着4个字节，前两个字节意思和其他一样
 * 第三个字节是方法传递参数需要的slot数
 * 第四个值必须为0
 *
 * @author Gao Han
 * @date: 2018年04月25日
 */
public class InvokeInterface implements Instruction {

  private int index;

  @Override
  public void fetchOperands(BytecodeReader reader) {
    index = reader.getUnsignedShort();
    reader.getUnsignedByte();
    reader.getUnsignedByte();
  }

  @Override
  public void execute(Frame frame) {
    RtConstantPool cp = frame.getMethod().getClazz().getConstantPool();
    CpInfos.InterfaceMethodRef methodRef = (CpInfos.InterfaceMethodRef) cp.getConstant(index);
    ReClass.Method resolvedMethod = methodRef.resolvedInterfaceMethod();

    if (resolvedMethod.isStatic() || resolvedMethod.isPrivate()) {
      throw new Error("java.lang.IncompatibleClassChangeError");
    }

    // get `this` object which implement interface
    ReObject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgslotCount() - 1);
    if (ref == null) {
      throw new Error("java.lang.NullPointerException");
    }
    if (!ref.getClazz().isImplements(methodRef.resolvedClass())) {
      throw new Error("java.lang.IncompatibleClassChangeError");
    }

    ReClass.Method methodToBeInvoked = MethodUtils.lookupMethodInClass(ref.getClazz(),
        methodRef.getName(), methodRef.getDescriptor());
    if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
      throw new Error("java.lang.AbstractMethodError");
    }
    if (!methodToBeInvoked.isPublic()) {
      throw new Error("java.lang.IllegalAccessError");
    }

    MethodUtils.invokeMethod(frame, methodToBeInvoked);
  }

}

