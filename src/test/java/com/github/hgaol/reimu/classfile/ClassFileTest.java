package com.github.hgaol.reimu.classfile;

import com.github.hgaol.reimu.classpath.ClassPath;
import com.github.hgaol.reimu.util.VMUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Gao Han
 * @date: 2018年04月26日
 */
public class ClassFileTest {

  private static final Logger logger = LoggerFactory.getLogger(ClassFileTest.class);

  @Before
  public void before() {
    ToStringBuilder.setDefaultStyle(new VMUtils.ReimuToStringStyle());
  }

  @Test
  public void parsejlObjectTest() {
    ClassPath classPath = new ClassPath("", "target/test-classes");
    String javaHome = System.getenv("JAVA_HOME");

    byte[] data = classPath.readClass("java/lang/Object");
    ClassFile cf = ClassFileUtil.parse(data);

//    System.out.println(ToStringBuilder.reflectionToString(cf));
    Assert.assertEquals(0xCAFEBABE, cf.magic);
    Assert.assertEquals(0, cf.minorVersion);
    Assert.assertEquals(52, cf.majorVersion);
    Assert.assertEquals(78, cf.constantPool.constants.length);
    Assert.assertEquals("java/lang/Object", cf.getClassName());
    Assert.assertEquals("", cf.getSuperClassName());
  }

}
