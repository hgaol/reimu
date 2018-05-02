package com.github.hgaol.reimu.nativee.java.lang;

import com.github.hgaol.reimu.nativee.INativeMethod;
import com.github.hgaol.reimu.nativee.NativeMethodPool;
import com.github.hgaol.reimu.rtda.Thread;
import com.github.hgaol.reimu.rtda.Frame;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.ReObject;

import java.util.Arrays;

/**
 * @author Gao Han
 * @date: 2018年04月27日
 */
public class NaThrowable {

  private static final String jlThrowable = "java/lang/Throwable";

  public static void init() {
    NativeMethodPool.register(jlThrowable, "fillInStackTrace", "(I)Ljava/lang/Throwable;", fillInStackTrace);
  }

  private static INativeMethod fillInStackTrace = (Frame frame) -> {
    // todo
    ReObject thisObj = frame.getLocalVars().getThis();
    frame.getOperandStack().pushRef(thisObj);

    StackTraceElement[] stes = createStackTraceElements(thisObj, frame.getThread());
    thisObj.setExtra(stes);
  };

  private static StackTraceElement[] createStackTraceElements(ReObject tObj, Thread thread) {
    int skip = distanceToObject(tObj.getClazz()) + 2;
    Frame[] fs = thread.getFrames();
    Frame[] frames = Arrays.copyOfRange(fs, skip, fs.length);

    StackTraceElement[] stes = new StackTraceElement[frames.length];
    for (int i = 0; i < frames.length; i++) {
      stes[i] = createStackTraceElement(frames[i]);
    }
    return stes;
  }

  private static StackTraceElement createStackTraceElement(Frame frame) {
    return new StackTraceElement(frame);
  }

  private static int distanceToObject(ReClass clazz) {
    int distance = 0;
    for (ReClass c = clazz.getSuperClass(); c != null; c = c.getSuperClass()) {
      distance++;
    }
    return distance;
  }

  public static class StackTraceElement {
    String fileName;
    String className;
    String methodName;
    int lineNumber;

    StackTraceElement(Frame frame) {
      ReClass.Method method = frame.getMethod();
      if (method == null) {
        return;
      }
      ReClass clazz = method.getClazz();
      fileName = clazz.getSourceFile();
      className = clazz.getName();
      methodName = method.getName();
      lineNumber = method.getLineNumber(frame.getNextPc() - 1);
    }

    @Override
    public String toString() {
      return "at " + className + "." + methodName + "(" + fileName + ":" + lineNumber + ")";
    }
  }

}
