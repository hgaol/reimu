package com.github.hgaol.reimu.classpath;

import com.github.hgaol.reimu.util.VMUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Gao Han
 * @date: 2018年04月26日
 */
public class ClassPathTest {

  private static final Logger logger = LoggerFactory.getLogger(ClassPathTest.class);

  @Before
  public void before() {
    ToStringBuilder.setDefaultStyle(new VMUtils.ReimuToStringStyle());
  }

  @Test
  public void classPathTest1() {
    ClassPath classPath = new ClassPath("", "target/test-classes");
    String javaHome = System.getenv("JAVA_HOME");

    Assert.assertEquals(String.join(File.separator, javaHome, "jre", "lib", "*"), classPath.getBootClasspath().getPath());
    Assert.assertEquals(String.join(File.separator, javaHome, "jre", "lib", "ext", "*"), classPath.getExtClasspath().getPath());
    Assert.assertEquals(new File("target/test-classes").getAbsolutePath(), classPath.getUserClasspath().getPath());
  }

  @Test
  public void classPathTest2() {
    ClassPath classPath = new ClassPath("", "target/test-classes");
    String javaHome = System.getenv("JAVA_HOME");

    Assert.assertNotNull(classPath.readClass("java/lang/Object"));
    Assert.assertNotNull(classPath.readClass("com/github/hgaol/reimu/example/HelloWorld"));
  }

}
