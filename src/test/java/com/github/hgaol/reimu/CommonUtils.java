package com.github.hgaol.reimu;

import com.github.hgaol.reimu.classpath.ClassPath;
import com.github.hgaol.reimu.cmd.CmdInfo;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.ReClassLoader;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Gao Han
 * @date: 2018年04月26日
 */
public class CommonUtils {

  private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

  public static void invokeTest(String[] args) {
    CmdInfo cmd = new CmdInfo(args);
    ClassPath classPath = new ClassPath(cmd.xjre, cmd.cp);
    logger.debug("\n\tclasspath: {}, class: {}, args: {}\n", ToStringBuilder.reflectionToString(classPath, ToStringStyle.MULTI_LINE_STYLE), cmd.cls, Arrays.toString(cmd.args));
    // 2. 读取文件
    ReClassLoader loader = new ReClassLoader(classPath);
    // zip获取的文件名都是/为分隔符
    String className = cmd.cls.replace(".", "/");
    ReClass mainClass = loader.loadClass(className);
    ReClass.Method mainMethod = mainClass.getMainMethod();

    assertNotNull(mainMethod);
    try {
      Interpreter.interpret(mainMethod, cmd.args);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      assertEquals(true, true);
    }
  }

}
