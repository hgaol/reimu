package com.github.hgaol.reimu.instructions.base;

import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月10日
 */
public interface Instruction {
  void fetchOperands(BytecodeReader reader);
  void execute(Frame frame);
}
