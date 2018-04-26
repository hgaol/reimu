package com.github.hgaol.reimu.rtda.heap;

import com.github.hgaol.reimu.classfile.ClassFile;
import com.github.hgaol.reimu.classfile.ClassFileUtil;
import com.github.hgaol.reimu.classpath.ClassPath;
import com.github.hgaol.reimu.rtda.Slots;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gao Han
 * @date: 2018年04月12日
 */
public class ReClassLoader {

  private static final Logger logger = LoggerFactory.getLogger(ReClassLoader.class);

  private ClassPath classPath;
  /**
   * 可以认为简单的方法区，保存已加载的Class
   */
  private Map<String, ReClass> classMap;

  public ReClassLoader(ClassPath classPath) {
    this.classPath = classPath;
    this.classMap = new HashMap<>();
  }

  /**
   * 在classpath中读取class信息，并保存在classMap中
   *
   * @param name 类的全限定名
   * @return
   */
  public ReClass loadClass(String name) {
    ReClass clazz = classMap.get(name);
    if (clazz != null) {
      return clazz;
    }
    // 加载数组类
    if (name.charAt(0) == '[') {
      return loadArrayClass(name);
    }
    return loadNonArrayClass(name);
  }

  private ReClass loadArrayClass(String name) {
    ReClass arrayClass = new ReClass();
    arrayClass.setAccessFlags(AccessFlags.ACC_PUBLIC)
        .setName(name)
        .setLoader(this)
        .setInitStarted(true)
        .setSuperClass(loadClass("java/lang/Object"))
        .setInterfaces(new ReClass[]{loadClass("java/lang/Cloneable"), loadClass("java/io/Serializable")});
    this.classMap.put(name, arrayClass);
    return arrayClass;
  }

  /**
   * <h4>加载非数组类型的类</h4>
   *
   * @param name Class全限定名
   * @return 解析好的Class对象
   */
  private ReClass loadNonArrayClass(String name) {
    logger.debug("[Start load class {}]\n", name);
    byte[] data = readClass(name);
    ReClass clazz = defineClass(data);
    link(clazz);
    logger.debug("[Loaded class {}]\n", name);
    return clazz;
  }

  /**
   * @param name Class全限定名
   * @return
   */
  private byte[] readClass(String name) {
    byte[] data = classPath.readClass(name);
    if (data == null) {
      throw new Error("ReClassNotFoundException: " + name);
    }
    return data;
  }

  /**
   * 将byte code转换为Class类
   *
   * @param data class binary data
   * @return
   */
  private ReClass defineClass(byte[] data) {
    ReClass clazz = parseClass(data);
    clazz.setLoader(this);
    resolveSuperClass(clazz);
    resolveInterfaces(clazz);
    classMap.put(clazz.getName(), clazz);
    return clazz;
  }

  /**
   * 递归加载父类
   *
   * @param clazz class
   */
  private void resolveSuperClass(ReClass clazz) {
    if (!clazz.getName().equals("java/lang/Object")) {
      clazz.setSuperClass(clazz.getLoader().loadClass(clazz.getSuperClassName()));
    }
  }

  /**
   * 加载接口
   *
   * @param clazz
   */
  private void resolveInterfaces(ReClass clazz) {
    clazz.setInterfaces(new ReClass[clazz.getInterfaceNames().length]);
    for (int i = 0; i < clazz.getInterfaceNames().length; i++) {
      clazz.getInterfaces()[i] = clazz.getLoader().loadClass(clazz.getInterfaceNames()[i]);
    }
  }

  private void link(ReClass clazz) {
    verify(clazz);
    prepare(clazz);
  }

  private void verify(ReClass clazz) {
    // TODO
  }

  /**
   * 解析类的实例成员变量、静态成员变量、分配并初始化static final类型的成员变量
   *
   * @param clazz class
   */
  private void prepare(ReClass clazz) {
    calcInstantceFieldSlotIds(clazz);
    calcStaticFieldSlotIds(clazz);
    allocAndInitStaticVars(clazz);
  }

  /**
   * 计算实例成员变量对应的slotId
   *
   * @param clazz class
   */
  private void calcInstantceFieldSlotIds(ReClass clazz) {
    int slotId = 0;
    // 如果父类不为空，则slotId从父类的最后一个开始（排在父类后面，父类会先被加载）
    if (clazz.getSuperClass() != null) {
      slotId = clazz.getSuperClass().getInstanceSlotCount();
    }
    for (ReClass.Field field : clazz.getFields()) {
      if (!field.isStatic()) {
        field.slotId = slotId;
        slotId++;
        if (field.isLongOrDouble()) {
          slotId++;
        }
      }
    }
    clazz.setInstanceSlotCount(slotId);
  }

  /**
   * 计算静态成员变量对应的slotId，静态的不需要考虑父类的
   *
   * @param clazz
   */
  private void calcStaticFieldSlotIds(ReClass clazz) {
    int slotId = 0;
    for (ReClass.Field field : clazz.getFields()) {
      if (field.isStatic()) {
        field.slotId = slotId;
        slotId++;
        if (field.isLongOrDouble()) {
          slotId++;
        }
      }
    }
    clazz.setStaticSlotCount(slotId);
  }

  private void allocAndInitStaticVars(ReClass clazz) {
    clazz.setStaticVars(new Slots(clazz.getStaticSlotCount()));
    for (ReClass.Field field : clazz.getFields()) {
      initStaticFinalVar(clazz, field);
    }
  }

  /**
   * 设置Class的static final的值，也就是设置{@link ReClass}的staticVars
   *
   * @param clazz
   * @param field
   */
  private void initStaticFinalVar(ReClass clazz, ReClass.Field field) {
    Slots staticVars = clazz.getStaticVars();
    RtConstantPool cp = clazz.getConstantPool();
    int cpIndex = field.constValueIndex;
    int slotId = field.slotId;

    if (cpIndex > 0) {
      switch (field.descriptor) {
        // 基本类型boolean
        case "Z":
          // 基本类型byte
        case "B":
          // 基本类型char
        case "C":
          // 基本类型short
        case "S":
          // 基本类型int
        case "I":
          staticVars.setInt(slotId, (int) cp.getConstant(cpIndex));
          break;
        // 基本类型long
        case "J":
          staticVars.setLong(slotId, (long) cp.getConstant(cpIndex));
          break;
        // 基本类型float
        case "F":
          staticVars.setFloat(slotId, (float) cp.getConstant(cpIndex));
          break;
        // 基本类型double
        case "D":
          staticVars.setDouble(slotId, (double) cp.getConstant(cpIndex));
          break;
        // String类型
        case "Ljava/lang/String;":
          String key = (String) cp.getConstant(cpIndex);
          ReObject reStr = StringPool.getReString(clazz.getLoader(), key);
          staticVars.setRef(slotId, reStr);
          break;
        default:
          throw new Error("todo");
      }
    }
  }

  public static ReClass parseClass(byte[] data) {
    ClassFile cf = ClassFileUtil.parse(data);
    return new ReClass(cf);
  }

}

