package com.github.hgaol.reimu.instructions.loads;

import com.github.hgaol.reimu.instructions.base.NoOperandsInstruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.heap.ReObject;

/**
 * @author Gao Han
 * @date: 2018年04月26日
 */
public class XAloads {

  // Load reference from array
  public static class AAload extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      ReObject[] refs = arrRef.getRefs();
      checkIndex(refs.length, index);
      stack.pushRef(refs[index]);
    }
  }

  // Load byte or boolean from array
  public static class BAload extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      byte[] refs = arrRef.getBytes();
      checkIndex(refs.length, index);
      stack.pushInt(refs[index]);
    }
  }

  // Load char from array
  public static class CAload extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      char[] refs = arrRef.getChars();
      checkIndex(refs.length, index);
      stack.pushInt(refs[index]);
    }
  }

  public static class DAload extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      double[] refs = arrRef.getDoubles();
      checkIndex(refs.length, index);
      stack.pushDouble(refs[index]);
    }
  }

  public static class FAload extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      float[] refs = arrRef.getFloats();
      checkIndex(refs.length, index);
      stack.pushFloat(refs[index]);
    }
  }

  public static class IAload extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      int[] refs = arrRef.getInts();
      checkIndex(refs.length, index);
      stack.pushInt(refs[index]);
    }
  }

  public static class LAload extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      long[] refs = arrRef.getLongs();
      checkIndex(refs.length, index);
      stack.pushLong(refs[index]);
    }
  }

  public static class SAload extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
      OperandStack stack = frame.getOperandStack();
      int index = stack.popInt();
      ReObject arrRef = stack.popRef();

      checkNotNull(arrRef);
      short[] refs = arrRef.getShorts();
      checkIndex(refs.length, index);
      stack.pushInt(refs[index]);
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
