package com.github.hgaol.reimu;

import com.github.hgaol.reimu.classfile.ClassFile;
import com.github.hgaol.reimu.classfile.ClassFileUtil;
import com.github.hgaol.reimu.classfile.MemberInfo;
import com.github.hgaol.reimu.classpath.ClassPath;
import com.github.hgaol.reimu.cmd.CmdInfo;

import java.util.Arrays;

import static com.github.hgaol.reimu.util.EchoUtils.echof;
import static com.github.hgaol.reimu.util.EchoUtils.echoln;

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
    echof("classpath: %s, class: %s, args: %s\n", classPath, cmd.cls, Arrays.toString(cmd.args));
    // 2. 读取文件
    // zip获取的文件名都是/为分隔符
    String className = cmd.cls.replace(".", "/");
    byte[] data = classPath.readClass(className);

    if (data == null) {
      System.err.println("Could not find or load class " + cmd.cls);
    }

    // 3. 解析文件
    ClassFile cf = ClassFileUtil.parse(data);
    echoln(cf);

    MemberInfo mainMethod = getMainMethod(cf);
    if (mainMethod == null) {
      echof("Main method not found in class %s\n", cmd.cls);
    } else {
      Interpreter.interpret(mainMethod);
    }
  }

  private static MemberInfo getMainMethod(ClassFile cf) {
    for (MemberInfo memberInfo : cf.methods) {
      if ("main".equals(memberInfo.getName()) && "([Ljava/lang/String;)V".equals(memberInfo.getDescriptor())) {
        return memberInfo;
      }
    }
    return null;
  }

}
