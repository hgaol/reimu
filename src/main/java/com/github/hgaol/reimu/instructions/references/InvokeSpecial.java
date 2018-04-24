package com.github.hgaol.reimu.instructions.references;

import com.github.hgaol.reimu.instructions.base.Index16Instruction;
import com.github.hgaol.reimu.rtda.Frame;

/**
 * @author Gao Han
 * @date: 2018年04月24日
 */
public class InvokeSpecial extends Index16Instruction {
  @Override
  public void execute(Frame frame) {
    // todo: hack
    frame.getOperandStack().popRef();
  }
}
