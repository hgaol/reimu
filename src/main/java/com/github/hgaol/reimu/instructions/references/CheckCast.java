package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.Index16Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.heap.Class;
import com.github.hgaol.reimu.rtda.heap.CpInfos;
import com.github.hgaol.reimu.rtda.heap.ReObject;
import com.github.hgaol.reimu.rtda.heap.RtConstantPool;

/**
 * @author Gao Han
 * @date: 2018年04月23日
 */
public class CheckCast extends Index16Instruction {
  @Override
  public void execute(Frame frame) {
    OperandStack stack = frame.getOperandStack();
    ReObject ref = stack.popRef();
    stack.pushRef(ref);
    if (ref == null) {
      return;
    }

    RtConstantPool cp = frame.getMethod().getClazz().getConstantPool();
    CpInfos.ClassRef classRef = (CpInfos.ClassRef) cp.getConstant(index);
    Class clazz = classRef.resolvedClass();

    if (!ref.isInstanceOf(clazz)) {
      throw new Error("java.lang.ClassCastException");
    }
  }
}
