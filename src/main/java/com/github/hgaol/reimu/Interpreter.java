package com.github.hgaol.reimu;

import com.github.hgaol.reimu.instructions.InstructionFactory;
import com.github.hgaol.reimu.instructions.base.BytecodeReader;
import com.github.hgaol.reimu.instructions.base.Instruction;
import com.github.hgaol.reimu.rtda.Thread;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Gao Han
 * @date: 2018年04月11日
 */
public class Interpreter {

  private static final Logger logger = LoggerFactory.getLogger(Interpreter.class);

  public static void interpret(ReClass.Method method) {
    Thread thread = new Thread();
    Frame frame = thread.newFrame(method);
    thread.pushFrame(frame);

    try {
      loop(thread);
    } catch (Exception e) {
      logFrame(thread);
      logger.error(e.getMessage(), e);
    }
  }

  private static void loop(Thread thread) {
    BytecodeReader reader = new BytecodeReader();

    while (true) {
      Frame frame = thread.topFrame();
      int pc = frame.getNextPc();
      thread.setPc(pc);

      // decode
      reader.reset(frame.getMethod().getCode(), pc);
      byte opcode = reader.getByte();
      Instruction inst = InstructionFactory.newInstruction(opcode);
      assert inst != null;
      inst.fetchOperands(reader);
      frame.setNextPc(reader.getPc());

      // execute
      logger.debug("class: {} method: {} pc: #{} inst: {} {} \n\tlocal-vars: {}, \n\toperand-stack: {}\n",
          frame.getMethod().getClazz().getName(),
          frame.getMethod().getName(),
          pc, inst.getClass().getSimpleName(),
          ToStringBuilder.reflectionToString(inst, ToStringStyle.SIMPLE_STYLE),
          ToStringBuilder.reflectionToString(frame.getLocalVars()),
          ToStringBuilder.reflectionToString(frame.getOperandStack()));
      inst.execute(frame);
      if (thread.isStackEmpty()) {
        break;
      }
    }
  }

  private static void logFrame(Thread thread) {
    if (!thread.isStackEmpty()) {
      Frame frame = thread.popFrame();
      ReClass.Method method = frame.getMethod();
      ReClass clazz = method.getClazz();
      logger.info(">> pc:{} {}.{}{} \n",
          frame.getNextPc(),
          clazz.getName(),
          method.getName(),
          method.getDescriptor());
    }
  }

}
