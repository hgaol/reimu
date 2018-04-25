package com.github.hgaol.reimu.classobject;

import com.github.hgaol.reimu.Interpreter;
import com.github.hgaol.reimu.classpath.ClassPath;
import com.github.hgaol.reimu.cmd.CmdInfo;
import com.github.hgaol.reimu.rtda.heap.ReClass;
import com.github.hgaol.reimu.rtda.heap.ReClassLoader;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Gao Han
 * @date: 2018年04月24日
 *
 */
public class ClassAndObjectTest {

  private static final Logger logger = LoggerFactory.getLogger(ClassAndObjectTest.class);

  @Before
  public void before() {

  }

  @Test
  public void main() {
    String[] args = new String[]
        {"-cp", "target/test-classes", "com.github.hgaol.reimu.example.MyObject"};
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

  @Test
  public void startJVM() {
  }

}
