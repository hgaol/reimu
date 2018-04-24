package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.Index16Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.heap.CpInfos;
import com.github.hgaol.reimu.rtda.heap.RtConstantPool;

/**
 * Invoke instance method; dispatch based on class
 *
 * @author Gao Han
 * @date: 2018年04月24日
 */
public class InvokeVirtual extends Index16Instruction {
  @Override
  public void execute(Frame frame) {
    // todo: hack
    RtConstantPool cp = frame.getMethod().getClazz().getConstantPool();
    CpInfos.MethodRef methodRef = (CpInfos.MethodRef) cp.getConstant(index);

    if (methodRef.getName().equals("println")) {
      OperandStack stack = frame.getOperandStack();
      switch (methodRef.getDescriptor()) {
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
          throw new Error("println: " + methodRef.getDescriptor());

      }
    }
  }
}
