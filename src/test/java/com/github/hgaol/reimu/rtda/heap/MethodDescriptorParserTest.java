package com.github.hgaol.reimu.rtda.heap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author Gao Han
 * @date: 2018年04月25日
 */
public class MethodDescriptorParserTest {

  private static final Logger logger = LoggerFactory.getLogger(MethodDescriptorParserTest.class);

  @Before
  public void before() {
    ToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE);
  }

  @Test
  public void parse1() {
    ReClass.Method.MethodDescriptorParser parser = new ReClass.Method.MethodDescriptorParser();
    ReClass.Method.MethodDescriptor descriptor = parser.parse("(D)V");

    logger.info(ToStringBuilder.reflectionToString(descriptor));

    assertEquals(descriptor.returnType, "V");
    assertEquals(descriptor.parameterTypes.size(), 1);
    assertEquals(descriptor.parameterTypes.get(0), "D");
  }

  @Test
  public void parse2() {
    ReClass.Method.MethodDescriptorParser parser = new ReClass.Method.MethodDescriptorParser();
    ReClass.Method.MethodDescriptor descriptor = parser.parse("([Ljava/lang/String;DDD)D");

    logger.info(ToStringBuilder.reflectionToString(descriptor));

    assertEquals(descriptor.returnType, "D");
    assertEquals(descriptor.parameterTypes.size(), 4);
    assertEquals(descriptor.parameterTypes.get(0), "[Ljava/lang/String;");
  }

}
