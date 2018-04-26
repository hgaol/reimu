package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.BytecodeReader;
import com.github.hgaol.reimu.instructions.base.Instruction;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.OperandStack;
import com.github.hgaol.reimu.rtda.heap.CpInfos;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.ReObject;
import com.github.hgaol.reimu.rtda.heap.RtConstantPool;

import java.util.Arrays;

/**
 * @author Gao Han
 * @date: 2018年04月26日
 */
public class MultiANewArray implements Instruction {

  private int index;
  private int dimensions;

  @Override
  public void fetchOperands(BytecodeReader reader) {
    index = reader.getUnsignedShort();
    dimensions = reader.getUnsignedByte();
  }

  @Override
  public void execute(Frame frame) {
    RtConstantPool cp = frame.getMethod().getClazz().getConstantPool();
    CpInfos.ClassRef classRef = (CpInfos.ClassRef) cp.getConstant(index);
    ReClass arrClass = classRef.resolvedClass();

    OperandStack stack = frame.getOperandStack();
    int[] counts = popAndCheckCounts(stack, dimensions);
    ReObject arr = newMultiDimensionalArray(counts, arrClass);
    stack.pushRef(arr);
  }

  /**
   * 计算数组维度数组
   * @param stack
   * @param dimensions 数组维度
   * @return 每个维度个数的数组
   */
  private int[] popAndCheckCounts(OperandStack stack, int dimensions) {
    int[] counts = new int[dimensions];
    for (int i = dimensions - 1; i >= 0; i--) {
      counts[i] = stack.popInt();
      if (counts[i] < 0) {
        throw new Error("java.lang.NegativeArraySizeException");
      }
    }

    return counts;
  }

  /**
   * 递归创建数组
   * @param counts
   * @param arrClass
   * @return
   */
  private ReObject newMultiDimensionalArray(int[] counts, ReClass arrClass) {
    int count = counts[0];
    ReObject arr = arrClass.newArray(count);

    if (counts.length > 1) {
      ReObject[] refs = arr.getRefs();
      for (int i = 0; i < refs.length; i++) {
        refs[i] = newMultiDimensionalArray(Arrays.copyOfRange(counts, 1, counts.length), arrClass.componentClass());
      }
    }
    return null;
  }

}

