package com.github.hgaol.reimu.instructions.constants;

import com.github.hgaol.reimu.instructions.base.Index16Instruction;
import com.github.hgaol.reimu.instructions.base.Index8Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.heap.RtConstantPool;

/**
 * 从运行时常量池中加载常量值，并把它推入操作数栈
 *
 * @author Gao Han
 * @date: 2018年04月23日
 */
public class Ldcs {
  public static class Ldc extends Index8Instruction {
    @Override
    public void execute(Frame frame) {
      _ldc(frame, index);
    }
  }

  public static class LdcW extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
      _ldc(frame, index);
    }
  }

  public static class Ldc2W extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      RtConstantPool cp = frame.getMethod().getClazz().getConstantPool();
      Object c = cp.getConstant(index);

      ObjType type = ObjType.valueOf(c.getClass().getSimpleName());
      switch (type) {
        case Long:
          stack.pushLong((long) c);
          break;
        case Double:
          stack.pushDouble((double) c);
          break;
        default:
          throw new Error("java.lang.ClassFormatError");
      }
    }
  }

  public static void _ldc(Frame frame, int index) {
    OperandStack stack = frame.getOperandStack();
    RtConstantPool cp = frame.getMethod().getClazz().getConstantPool();
    Object c = cp.getConstant(index);

    ObjType type = ObjType.valueOf(c.getClass().getSimpleName());
    switch (type) {
      case Integer:
        stack.pushInt((int) c);
        break;
      case Long:
        stack.pushLong((long) c);
        break;
      // todo
//      case String:
//      case ClassRef:
      default:
        throw new Error("todo: ldc");
    }
  }

  enum ObjType {
    Integer,
    Long,
    Double,
    String,
    ClassRef
  }

}
