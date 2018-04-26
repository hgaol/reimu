package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.heap.ReObject;

/**
 * @author Gao Han
 * @date: 2018年04月26日
 */
public class ArrayLength extends NoOperandsInstruction {

  @Override
  public void execute(Frame frame) {
    OperandStack stack = frame.getOperandStack();
    ReObject arr = stack.popRef();
    if (arr == null) {
      throw new Error("java.lang.NullPointerException");
    }

    int length = arr.getArrayLenth();
    stack.pushInt(length);
  }

}
