package com.github.hgaol.reimu.instructions.base;

import com.github.hgaol.reimu.rtda.Thread;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.heap.ReClass;

/**
 * @author Gao Han
 * @date: 2018年04月26日
 */
public class ClassInitLogic {

  // jvms 5.5
  public static void initClass(Thread thread, ReClass clazz) {
    clazz.startInit();
    scheduleClinit(thread, clazz);
    // 如果superClass也没有被初始化，初始化superClass，这样保证父类的clinit方法在栈顶，先初始化
    initSuperClass(thread, clazz);
  }

  private static void scheduleClinit(Thread thread, ReClass clazz) {
    ReClass.Method clinit = clazz.getClinitMethod();
    if (clinit != null) {
      // 执行clinit方法，clinit方法没有参数，所以不需要传递
      Frame newFrame = thread.newFrame(clinit);
      thread.pushFrame(newFrame);
    }
  }

  private static void initSuperClass(Thread thread, ReClass clazz) {
    if (!clazz.isInterface()) {
      ReClass superClass = clazz.getSuperClass();
      if (superClass != null && !superClass.isInitStarted()) {
        initClass(thread, superClass);
      }
    }
  }

}
