package com.github.hgaol.reimu.array;

import com.github.hgaol.reimu.invokemethod.InvokeMethodTest;
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
public class ArrayTest {

  private static final Logger logger = LoggerFactory.getLogger(InvokeMethodTest.class);

  @Before
  public void before() {
    ToStringBuilder.setDefaultStyle(new VMUtils.ReimuToStringStyle());
  }

  @Test
  public void arrayTest() {
    String[] args = new String[]
        {"-cp", "target/test-classes", "com.github.hgaol.reimu.example.BubbleSort"};
    invokeTest(args);
  }

  @Test
  public void stringHelloTest() {
    String[] args = new String[]
        {"-cp", "target/test-classes", "com.github.hgaol.reimu.example.HelloWorld"};
    invokeTest(args);
  }

}
