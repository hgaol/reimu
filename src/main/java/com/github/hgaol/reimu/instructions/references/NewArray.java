package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.BytecodeReader;
import com.github.hgaol.reimu.instructions.base.Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.ReClassLoader;
import com.github.hgaol.reimu.rtda.heap.ReObject;

/**
 * @author Gao Han
 * @date: 2018年04月26日
 */
public class NewArray implements Instruction {

  private int atype;

  @Override
  public void fetchOperands(BytecodeReader reader) {
    atype = reader.getUnsignedByte();
  }

  @Override
  public void execute(Frame frame) {
    OperandStack stack = frame.getOperandStack();
    int count = stack.popInt();
    if (count < 0) {
      throw new Error("java.lang.NegativeArraySizeException");
    }

    ReClassLoader classLoader = frame.getMethod().getClazz().getLoader();
    ReClass arrClass = getPrimitiveArrayClass(classLoader, atype);
    ReObject arr = arrClass.newArray(count);
    stack.pushRef(arr);
  }

  public ReClass getPrimitiveArrayClass(ReClassLoader loader, int atype) {
    switch (atype) {
      case TypeCode.AT_BOOLEAN:
        return loader.loadClass("[Z");
      case TypeCode.AT_BYTE:
        return loader.loadClass("[B");
      case TypeCode.AT_CHAR:
        return loader.loadClass("[C");
      case TypeCode.AT_SHORT:
        return loader.loadClass("[S");
      case TypeCode.AT_INT:
        return loader.loadClass("[I");
      case TypeCode.AT_LONG:
        return loader.loadClass("[J");
      case TypeCode.AT_FLOAT:
        return loader.loadClass("[F");
      case TypeCode.AT_DOUBLE:
        return loader.loadClass("[D");
      default:
        throw new Error("Invalid atype!");
    }
  }

  public static class TypeCode {
    public static final int AT_BOOLEAN = 4;
    public static final int AT_CHAR = 5;
    public static final int AT_FLOAT = 6;
    public static final int AT_DOUBLE = 7;
    public static final int AT_BYTE = 8;
    public static final int AT_SHORT = 9;
    public static final int AT_INT = 10;
    public static final int AT_LONG = 11;
  }

}
