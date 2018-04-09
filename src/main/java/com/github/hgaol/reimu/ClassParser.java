package com.github.hgaol.reimu;

import com.github.hgaol.reimu.classfile.ClassFile;
import com.github.hgaol.reimu.classfile.ClassFileUtil;
import com.github.hgaol.reimu.classpath.ClassPath;
import com.github.hgaol.reimu.cmd.CmdInfo;

import java.io.IOException;
import java.util.Arrays;

import static com.github.hgaol.reimu.util.EchoUtils.echof;
import static com.github.hgaol.reimu.util.EchoUtils.echoln;

/**
 * Hello world!
 */
public class ClassParser {
  public static void main(String[] args) throws IOException {
    CmdInfo cmd = new CmdInfo(args);
    if (cmd.version) {
      cmd.printVersion();
    } else if (cmd.help) {
      cmd.printHelp();
    } else {
      startJVM(cmd);
    }
//    Path path = Paths.get(args[0]);
//    byte[] data = Files.readAllBytes(path);
//    ClassFile cf = ClassFileUtil.parse(data);
//    echoln(cf);
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
  }

}
