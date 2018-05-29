package com.github.hgaol.reimu;

import com.github.hgaol.reimu.classpath.ClassPath;
import com.github.hgaol.reimu.cmd.CmdInfo;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.ReClassLoader;
import com.github.hgaol.reimu.util.VMUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Poor java virtual machine
 */
public class Reimu {

  private static final Logger logger = LoggerFactory.getLogger(Reimu.class);

  public static void main(String[] args) {
    CmdInfo cmd = new CmdInfo(args);
    init();
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
    logger.debug("\n\tclasspath: {}, class: {}, args: {}\n", ToStringBuilder.reflectionToString(classPath, ToStringStyle.MULTI_LINE_STYLE), cmd.cls, Arrays.toString(cmd.args));
    // 2. 读取文件
    ReClassLoader loader = new ReClassLoader(classPath);
    // zip获取的文件名都是/为分隔符
    String className = cmd.cls.replace(".", "/");
    ReClass mainClass = loader.loadClass(className);
    ReClass.Method mainMethod = mainClass.getMainMethod();

    if (mainMethod == null) {
      System.err.printf("Main method not found in class %s\n", cmd.cls);
    } else {
      Interpreter.interpret(mainMethod, cmd.args);
    }
  }

  private static void init() {
    ToStringBuilder.setDefaultStyle(new VMUtils.ReimuToStringStyle());
  }

}
