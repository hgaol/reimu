package com.github.hgaol.reimu.nativee;

import com.github.hgaol.reimu.util.VMUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.hgaol.reimu.CommonUtils.invokeTest;

/**
 * @author Gao Han
 * @date: 2018年04月26日
 */
public class NativeeTest {

  private static final Logger logger = LoggerFactory.getLogger(NativeeTest.class);

  @Before
  public void before() {
    ToStringBuilder.setDefaultStyle(new VMUtils.ReimuToStringStyle());
  }

  @Test
  public void nativeTest() {
    String[] args = new String[]
        {"-cp", "target/test-classes", "com.github.hgaol.reimu.example.GetClassTest"};
    invokeTest(args);
  }

  @Test
  public void stringTest() {
    String[] args = new String[]
        {"-cp", "target/test-classes", "com.github.hgaol.reimu.example.StringTest"};
    invokeTest(args);
  }

  @Test
  public void hashCodeTest() {
    String[] args = new String[]
        {"-cp", "target/test-classes", "com.github.hgaol.reimu.example.ObjectTest"};
    invokeTest(args);
  }

  @Test
  public void cloneTest() {
    // todo: inaccuracy
    String[] args = new String[]
        {"-cp", "target/test-classes", "com.github.hgaol.reimu.example.CloneTest"};
    invokeTest(args);
  }

}
