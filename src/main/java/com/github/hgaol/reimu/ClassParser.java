package com.github.hgaol.reimu;

import com.github.hgaol.reimu.classpath.ClassPath;
import com.github.hgaol.reimu.cmd.CmdInfo;
import com.github.hgaol.reimu.rtda.heap.Class;
import com.github.hgaol.reimu.rtda.heap.ReClassLoader;
import com.github.hgaol.reimu.util.EchoUtils;

import java.util.Arrays;

/**
 * Poor java virtual machine
 */
public class ClassParser {

  public static void main(String[] args) {
    CmdInfo cmd = new CmdInfo(args);
    if (cmd.version) {
      cmd.printVersion();
    } else if (cmd.help) {
      cmd.printHelp();
    } else {
      startJVM(cmd);
    }
  }

  public static void startJVM(CmdInfo cmd) {
    // 1. 创建classpath对象
    ClassPath classPath = new ClassPath(cmd.xjre, cmd.cp);
    EchoUtils.echof("classpath: %s, class: %s, args: %s\n", classPath, cmd.cls, Arrays.toString(cmd.args));
    // 2. 读取文件
    ReClassLoader loader = new ReClassLoader(classPath);
    // zip获取的文件名都是/为分隔符
    String className = cmd.cls.replace(".", "/");
    Class mainClass = loader.loadClass(className);
    Class.Method mainMethod = mainClass.getMainMethod();

    if (mainMethod == null) {
      System.err.printf("Main method not found in class %s\n", cmd.cls);
    } else {
      Interpreter.interpret(mainMethod);
    }
  }

}
