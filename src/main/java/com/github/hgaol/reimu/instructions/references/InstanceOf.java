package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.Index16Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.CpInfos;
import com.github.hgaol.reimu.rtda.heap.ReObject;
import com.github.hgaol.reimu.rtda.heap.RtConstantPool;

/**
 * 两个操作数
 * 1. unit16索引，类符号引用
 * 2. 对象引用，从操作数栈中弹出
 *
 * @author Gao Han
 * @date: 2018年04月23日
 */
public class InstanceOf extends Index16Instruction {
  @Override
  public void execute(Frame frame) {
    OperandStack stack = frame.getOperandStack();
    ReObject ref = stack.popRef();
    if (ref == null) {
      throw new Error("java.lang.NullPointerException");
    }

    RtConstantPool cp = frame.getMethod().getClazz().getConstantPool();
    CpInfos.ClassRef classRef = (CpInfos.ClassRef) cp.getConstant(index);
    ReClass clazz = classRef.resolvedClass();

    if (ref.isInstanceOf(clazz)) {
      stack.pushInt(1);
    } else {
      stack.pushInt(0);
    }
  }
}
