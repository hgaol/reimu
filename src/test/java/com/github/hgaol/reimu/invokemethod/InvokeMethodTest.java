package com.github.hgaol.reimu.invokemethod;

import com.github.hgaol.reimu.util.VMUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.hgaol.reimu.CommonUtils.invokeTest;

/**
 * @author Gao Han
 * @date: 2018年04月25日
 */
public class InvokeMethodTest {

  private static final Logger logger = LoggerFactory.getLogger(InvokeMethodTest.class);

  @Before
  public void before() {
    ToStringBuilder.setDefaultStyle(new VMUtils.ReimuToStringStyle());
  }

  @Test
  public void invokeDemoTest() {
    String[] args = new String[]
        {"-cp", "target/test-classes", "com.github.hgaol.reimu.example.InvokeDemo"};
    invokeTest(args);
  }

  @Test
  public void invokeFibonacciTest() {
    String[] args = new String[]
        {"-cp", "target/test-classes", "com.github.hgaol.reimu.example.FibonacciTest"};
    invokeTest(args);
  }


}
