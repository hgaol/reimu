package com.github.hgaol.reimu.instructions.stores;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.heap.ReObject;

/**
 * @author Gao Han
 * @date: 2018年04月26日
 */
public class XAStore {

  // Store into reference array
  public static class AAStore extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      ReObject ref = stack.popRef();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      ReObject[] refs = arrRef.getRefs();
      checkIndex(refs.length, index);
      refs[index] = ref;
    }
  }

  // Store into byte or boolean array
  public static class BAStore extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int val = stack.popInt();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      byte[] arr = arrRef.getBytes();
      checkIndex(arr.length, index);
      arr[index] = (byte) val;
    }
  }

  public static class CAStore extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int val = stack.popInt();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      char[] arr = arrRef.getChars();
      checkIndex(arr.length, index);
      arr[index] = (char) val;
    }
  }

  public static class DAStore extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      double val = stack.popDouble();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      double[] arr = arrRef.getDoubles();
      checkIndex(arr.length, index);
      arr[index] = val;
    }
  }

  public static class FAStore extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      float val = stack.popFloat();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      float[] arr = arrRef.getFloats();
      checkIndex(arr.length, index);
      arr[index] = val;
    }
  }

  public static class IAStore extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int val = stack.popInt();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      int[] arr = arrRef.getInts();
      checkIndex(arr.length, index);
      arr[index] = val;
    }
  }

  public static class LAStore extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      long val = stack.popLong();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      long[] arr = arrRef.getLongs();
      checkIndex(arr.length, index);
      arr[index] = val;
    }
  }

  public static class SAStore extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int val = stack.popInt();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      short[] arr = arrRef.getShorts();
      checkIndex(arr.length, index);
      arr[index] = (short) val;
    }
  }

  private static void checkNotNull(ReObject ref) {
    if (ref == null) {
      throw new Error("java.lang.NullPointerException");
    }
  }

  private static void checkIndex(int arrLen, int index) {
    if (index < 0 || index >= arrLen) {
      throw new Error("ArrayIndexOutOfBoundsException");
    }
  }
}
