package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.Index16Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.heap.CpInfos;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.ReObject;
import com.github.hgaol.reimu.rtda.heap.RtConstantPool;

/**
 * 两个操作数，一个表示创建哪种类型的数组(从字节码)，另一个表示数组长度(从操作数栈)
 * @author Gao Han
 * @date: 2018年04月26日
 */
public class ANewArray extends Index16Instruction {

  @Override
  public void execute(Frame frame) {
    RtConstantPool cp = frame.getMethod().getClazz().getConstantPool();
    CpInfos.ClassRef classRef = (CpInfos.ClassRef) cp.getConstant(index);
    ReClass componentClass = classRef.resolvedClass();

    OperandStack stack = frame.getOperandStack();
    int count = stack.popInt();
    if (count < 0) {
      throw new Error("java.lang.NegativeArraySizeException");
    }

    ReClass arrClass = componentClass.getArrayClass();
    ReObject arr = arrClass.newArray(count);
    stack.pushRef(arr);
  }

}
