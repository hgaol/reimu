package com.github.hgaol.reimu.handleexception;

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
public class ExceptionTest {

  private static final Logger logger = LoggerFactory.getLogger(ExceptionTest.class);

  @Before
  public void before() {
    ToStringBuilder.setDefaultStyle(new VMUtils.ReimuToStringStyle());
  }

  @Test
  public void exceptionTest() {
    String[] args = new String[]
        {"-cp", "target/test-classes", "com.github.hgaol.reimu.example.exceptions.ParseIntTest"};
    invokeTest(args);
  }

}
