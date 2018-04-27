package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.Index16Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.heap.CpInfos;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.ReObject;
import com.github.hgaol.reimu.rtda.heap.RtConstantPool;

/**
 * 给实例变量赋值，需要三个操作数。
 * 1. 常量池索引 2. 变量值 3. 对象引用，从操作数栈中弹出
 *
 * @author Gao Han
 * @date: 2018年04月23日
 */
public class PutField extends Index16Instruction {
  @Override
  public void execute(Frame frame) {
    ReClass.Method curMethod = frame.getMethod();
    ReClass curReClass = curMethod.getClazz();
    RtConstantPool cp = curReClass.getConstantPool();
    CpInfos.FieldRef fieldRef = (CpInfos.FieldRef) cp.getConstant(index);
    ReClass.Field field = fieldRef.resolvedField();

    if (field.isStatic()) {
      throw new Error("java.lang.IncompatibleClassChangeError");
    }
    if (field.isFinal()) {
      if (curReClass != field.getClazz() || !curMethod.getName().equals("<init>")) {
        throw new Error("java.lang.IllegalAccessError");
      }
    }

    String descriptor = field.getDescriptor();
    int slotId = field.getSlotId();
    OperandStack stack = frame.getOperandStack();

    Object val;
    ReObject ref;
    switch (descriptor.charAt(0)) {
      case 'Z':
      case 'B':
      case 'C':
      case 'S':
      case 'I':
        val = stack.popInt();
        ref = stack.popRef();
        if (ref == null) {
          throw new Error("java.lang.NullPointerException");
        }
        ref.getFields().setInt(slotId, (int) val);
        break;
      case 'F':
        val = stack.popFloat();
        ref = stack.popRef();
        if (ref == null) {
          throw new Error("java.lang.NullPointerException");
        }
        ref.getFields().setFloat(slotId, (float) val);
        break;
      case 'J':
        val = stack.popLong();
        ref = stack.popRef();
        if (ref == null) {
          throw new Error("java.lang.NullPointerException");
        }
        ref.getFields().setLong(slotId, (long) val);
        break;
      case 'D':
        val = stack.popDouble();
        ref = stack.popRef();
        if (ref == null) {
          throw new Error("java.lang.NullPointerException");
        }
        ref.getFields().setDouble(slotId, (double) val);
        break;
      case 'L':
      case '[':
        val = stack.popRef();
        ref = stack.popRef();
        if (ref == null) {
          throw new Error("java.lang.NullPointerException");
        }
        ref.getFields().setRef(slotId, (ReObject) val);
        break;
      default:
        // todo
    }

  }
}
