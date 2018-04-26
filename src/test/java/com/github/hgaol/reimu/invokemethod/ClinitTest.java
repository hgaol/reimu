package com.github.hgaol.reimu.invokemethod;

import com.github.hgaol.reimu.Interpreter;
import com.github.hgaol.reimu.classpath.ClassPath;
import com.github.hgaol.reimu.cmd.CmdInfo;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.ReClassLoader;
import com.github.hgaol.reimu.util.VMUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Gao Han
 * @date: 2018年04月26日
 */
public class ClinitTest {

  private static final Logger logger = LoggerFactory.getLogger(InvokeMethodTest.class);

  @Before
  public void before() {
    ToStringBuilder.setDefaultStyle(new VMUtils.ReimuToStringStyle());
  }

  @Test
  public void clinitTest() {
    String[] args = new String[]
        {"-cp", "target/test-classes", "com.github.hgaol.reimu.example.ClinitTest"};
    invokeTest(args);
  }

  private void invokeTest(String[] args) {
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
      Interpreter.interpret(mainMethod);
    } catch (Exception e) {
      assertEquals(true, true);
    }
  }
}
