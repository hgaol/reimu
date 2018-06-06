
# 总结

## 1. 搜索class文件

这个部分内容主要在`com.github.hgaol.reimu.classpath`包中

加载一个class文件会从三个地方，首先是启动类路径，即通常的`$JAVA_HOME/jre/lib/*`；接下来是扩展类路径，即通常的`$JAVA_HOME/jre/lib/ext/*`；最后是用户类路径，通过`-cp/--classpath`参数指定。

所以即使同样写了一个`java.lang.Object`类，加载的时候也是会加载启动类路径的。

这里可以通过`-jre`参数来制定启动类路径和扩展类路径的位置，对应`$JAVA_HOME/jre`，一般不用设置。

最终通过读取class文件获得其字节码，这部分还是比较直观的。

可以看下对应的测试(`ClassPathTest`)来看

## 2. 解析class文件

这部分主要在`com.github.hgaol.reimu.classfile`包中，根据[Java虚拟机规范](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html)进行解析，将字节码转换成`ClassFile`格式。这时候还不是方法区中的那个解析后的`Class`，虽然两者有很多相似之处。可以使用[classpy](https://github.com/zxh0/classpy)这个工具对照着看。

这里由于java没有`unsigned`类型，所以对于这种都是用大一号的类型，比如`unsigned short`使用`int`来表示等。

可参考`ClassFileTest`来简单了解下这个package的主要功能。

## 3. 运行时数据区

这部分主要在`com.github.hgaol.reimu.rtda`包中。

运行时数据区可分为两类：多线程共享的，线程私有的。

多线程共享的内存区域主要存放两类数据：类数据和类实例（对象）。对象数据存放在堆中，类数据存放在方法区中。

这次主要涉及线程私有的数据。

线程私有的运行时数据区用于辅助执行Java字节码。每个线程都有自己的pc寄存器，Java虚拟机栈。Java虚拟机栈又由栈帧(Stack Fram)构成，帧中保存方法执行的状态，包括局部变量表和操作数栈等。在任一时刻，某一县城肯定在执行某个方法，这个方法叫做线程的当前方法；执行该方法的帧叫当前帧；声明该方法的类叫当前类。如果当前方法是Java方法，则pc寄存器中存放当前正在执行的Java虚拟机指令的地址，如果是本地方法，pc寄存器中的值没意义。

`Thread`表示线程；`Stack`表示虚拟机栈，其中`Frame`表示具体的栈帧；`LocalVars`表示本地变量表；`OperandStack`表示操作数栈。

## 4. 指令集和解释器

这部分主要在`com.github.hgaol.reimu.instructions`包中。

这部分也比较容易理解，主要是一些基本的字节码指令。根据之前已经完成的内容，可以获得某个class的byte[]，然后解析为`ClassFile`，得到`methods`，找到`main`方法，逐条解析其中的内容。

下面为解析的主循环伪代码，java代码位于`com.github.hgaol.reimu.Interpreter.loop`中。

```java
while (stack is not empty) {
    // 获取当前pc
    pc = calculatePC();
    // 得到操作码
    opcode = bytecode[pc];
    // 生成指令
    inst = createInst(opcode);
    // 获取操作数
    inst.fetchOperands(bytecode);
    // 执行
    inst.excute();
}
```

这部分主要是解析字节码指令，其中主要涉及常量池、本地变量表(虚拟机栈)、操作数栈（虚拟机栈）。指令会根据该指令的含义和操作数，获取常量池中的值，对本地变量表、操作数栈进行数学运算、比较运算、跳转控制等操作。

## 5. 类和对象

这部分的代码还在`com.github.hgaol.reimu.rtda`包中，主要是关于线程共享的运行时数据区，包括方法区和运行时常量池的。

方法区主要存放从class文件获取的类信息，Java虚拟机规范没有明确规定方法区到底位于何处，此处使用`Map`来模拟。

这里使用`ReClass`这个类来存储类信息，内容和`ClassFile`大体类似，只不过这里会解析更多的内容。其中的`RtConstantPool`表示运行时常量池。

运行时常量池主要存放两类信息：字面量和符号引用。字面量包括证书、浮点数和字符串字面量；符号引用包括类符号引用、字段符号引用、方法符号引用和接口方法符号引用。对于字面量，会直接解析为对应的值；对于符号引用，主要解析为对应的引用对象，其中存着诸如className，descriptor，name等信息，方便之后用到的时候进行加载解析。

`ReClassLoader`是类加载器，其成员变量`classMap`为`Map<String, ReClass>`，即**Reimu**中的方法区实现，其中key为类的全限定名，value为解析后的类信息；类加载器还保存`ClassPath`，用于查找class文件。

加载一个类信息的步骤，可以从`ReClassLoader.loadCalss`开始看，下面是伪代码。

```go
public ReClass loadClass(String name) {
    // 类是否已加载
	ReClass clazz = classMap.get(name);
    if (clazz != null) {
      return clazz;
    }
    // 加载数组类
    if (name.charAt(0) == '[') {
      clazz = loadArrayClass(name);
    } else {
      // 加在非数组类
      clazz = loadNonArrayClass(name);
    }
    // 创建类对象
    ReClass jlClassClass = this.classMap.get("java/lang/Class");
    if (jlClassClass != null) {
      // 用 java/lang/Class 创建一个类对象，此时jClass.class -> java/lang/Class
      ReObject jClass = jlClassClass.newObject();
      // jClass.extra -> class; class.jClass -> jClass
      jClass.setExtra(clazz);
      clazz.setjClass(jClass);
    }
	return clazz;
}
```

其中加载数组类和创建类对象之后会说到，这里主要关注在加在非数组类。

加在非数组类主要有以下步骤：

1. 根据全限定名，加载字节码文件
2. 将字节码数组转换为`ClassFile`，再转换为`ReClass`
3. 设置本类加载器为该类的加载器
4. 加载父类，并设置该类的父类
5. 加载所有实现接口类，并设置该类的接口类
6. 将该类放入方法区

之后还有校验(verify)和准备(prepare)的步骤，由于不是重点，这里没有实现校验。准备（prepare）所做的事情主要是解析类的实例成员变量，静态成员变量、分配并初始化`static final`类型的成员变量(代码在`ReClassLoader.prepare`中)。

**类和字段符号引用解析**

还记得前面对运行时常量池加载，这里主要是对其进行解析，方便执行对应操作码时直接获得解析后的引用。

类的加载和解析可以看`CpInfos.SymRef.resolvedClass()`，返回解析过的class，如果没有加载则进行加载。

对字段符号引用的解析可以看`CpInfos.FieldRef.resolvedField()`，返回解析过的`Field`，如果没有加载则进行加载想用的类信息。

**类和对象相关指令**

`new`用来创建类实例；`putstatic`和`getstatic`用于存取静态变量；`putfield`和`getfield`用于存取实例变量；`instanceof`和`checkcast`指令用于判断对象是否属于某种类型；`ldc`系列指令把运行时常量池中的常量推到操作数栈顶。

相关细节可以看对应指令注释及代码的实现。

其中Reimu中的对象使用`ReObject`表示，`newObject`的过程主要是设置`ReClass`和设置`Slots`的过程。

这里说明下，使用已加载的类的时候，还需要判断该类是否已经初始化。类初始化的过程在`ClassInitLogic.initClass`中，主要是执行该类的父类和该类的`<clinit>`方法。

至此，类和对象的大体流程终于梳理好了。

## 6. 方法调用和返回

从调用的角度来看，方法可以分为两类：静态方法和实例方法。静态方法通过类来调用，实例方法通过对象引用来调用。静态方法是静态绑定的，也就是最终调用的是哪个方法在编译器就已经确定。实例方法则支持动态绑定，最终要调用哪个方法可能要推迟到运行期才能知道。

从实现角度，方法可分为三类：没有实现、用Java语言实现、用本地语言实现。

Java7之前，Java虚拟机规范一共提供了4条方法调用指令。`invokestatic`用来调用静态方法；`invokespecial`用来调用无需动态绑定的实例方法，包括构造函数、私有方法和通过super关键字调用的超类方法。剩下的情况则属于动态绑定。如果是针对接口类型的引用调用方法，就是用`invokeinterface`，否则使用`invokevirtual`。

首先，方法调用指令需要n+1个操作数，其中第一个操作数`unit16`索引，在字节码中紧跟在指令操作码的后面。通过这个索引，可以从当前类的运行时常量池中找到一个方法符号引用，解析这个符号引用就得到一个方法。剩下的n个操作数是要传递给被调用方法的参数，从操作数栈中弹出。

所有的方法调用指令`invoke_xxx`都类似，可看下`MethodUtils.invokeMethod(Frame, Method)`，归纳为以下步骤：

1. 通过运行时常量池获取`MethodRef`，进而获取`Method`或者通过this指针动态获取`Method`
2. 用被调用的method构建新的frame，push到thread中
3. copy方法的参数（旧frame的操作数栈 -> 新frame的本地变量表）
4. 下次loop就会调用新的方法了

下面看下如何解析常量池方法符号引用。可看`CpInfos.Method.resolveMethodRef`。首先解析持有该方法的类，然后`MethodUtils.lookupMethod`解析该方法，最后进行一系列的检查权限等操作。

**返回指令**。返回指令主要在方法执行完毕之后执行，把结果返回给调用方。可看下`Returns`中的指令实现。简单来说，就是从当前帧（被调用方法）的操作数栈中pop相应的值，push到调用方法中。

`invoke_static`：静态方法的执行也是遵循上面的步骤，需要判断是否是静态的，且该类需要解析并初始化。

`invoke_special`：用来调用无需动态绑定的实例方法。会根据实例对象进行各种检查权限的操作。

这里可能会迷，为啥static和special都是直接调用，好像和实例没啥关系啊。其实不然，调用静态方法的时候，传递的参数中没有传递`this`引用；而调用实例方法的时候会把该变量放在第1个位置（也就是本地变量表的第一个位置）。当需要使用实例的成员变量或者函数的时候，会使用到这个值，这些都是`javac`编译字节码的时候做的工作。

`invoke_virtual`：和`invoke_special`类似，区别在于是动态绑定方法。怎么动态绑定？其实是从操作数栈中获取方法的`this`参数，然后查找这个实例对象中的该方法实现。

```java
ReClass.Method methodToBeInvoked = MethodUtils.lookupMethodInClass(ref.getClazz(),
        methodRef.getName(), methodRef.getDescriptor());
```

`invoke_interface`：和`invoke_virtual`非常相似，区别是前面的`resolvedMethod`并不是真正的`Method`，因为没有具体实现，即`code`为null，这里会获取`this`参数，并查找this的class的方法实现（这里的this实例实现了对应的接口）。

之后的类的初始化前面提到过，实现了方法调用，就可以使用方法调用来实现类的初始化操作了。

## 7. 数组和字符串

**数组**

数组类和普通类不同，普通类从class文件加载，数组类由Java虚拟机在运行时生成。数组的类名是左方括号加上类型描述符。例如，`int[]`的类名是`[I`，`int[][]`的类名是`[[I`，`Object[]`的类名是`[Ljava/lang/Object;`。

数组创建使用`newarray`指令，引用类型数组由`anewarray`指令创建，还有专门的`multianewarray`创建多维数组。

普通对象中的实例变量，通过putfield和getfield存取；数组对象中存放的是数组元素，通过`<t>aload`和`<t>astore`系列之灵按索引存取。

数组对象同样使用`ReObject`表示。

**加载数组类**。可以看`ReClassLoader.loadArrayClass(String)`。数组类不需要初始化，超类为`java/lang/Object`，实现了`java/lang/Cloneable`、`java/lang/Serializable`接口。

`newarray`：用来创建基本类型数组。两个操作数，一个表示创建哪种类型的数组(从字节码)，另一个表示数组长度(从操作数栈)。其中会加载基本类型数组的类信息，就用到了`ReClassLoader.loadArrayClass(String)`。

`anewarray`：用来创建引用类型数组。两个操作数，一个表示创建哪种类型的数组(从字节码)，另一个表示数组长度(从操作数栈)。创建数组的操作在`ReClass.newArray`中，主要为设置`ReObject`的类类型，根据长度和类型创建相应的数组。

`arraylength`：获取数组长度，比较简单，从操作数栈pop出数组对象，然后得到长度。

`<t>load`：获取数组元素，推入操作数栈。

`<t>store`：按照索引给数组元素赋值，索引从操作数栈取。

`multianewarray`：创建多维数组，从字节码获取两个操作数，index（常量池中的类符号引用）和dimentions（数组维度）。可看下代码逻辑，主要是递归生成多维数组。

至此，碰到数组的操作码就不用害怕了。

**字符串**

字符串对象是不可变的，为了节约内存，Java虚拟机内部维护了一个字符串池。String类提供了`intern()`实例方法，可以把自己放入字符串池，对应代码`StringPool`。

池中维护了一个`Map<String, ReObject>`，其中key为该字符串，value为对应的Reimu中的Object。

其中`ReObject getReString(ReClassLoader loader, String key)`用来获取一个String的Object，首先获取char数组，然后创建`java/lang/String`的ReObject，之后将其`value`地段设置为刚刚的char数组。

这之后我们就实现了`StringPool`，可以使用Reimu处理字符串了。主要在于将传入的`String[]`转换为表示字符串数组的`ReObject`，设置到本地变量表的0位置。

## 8. 本地方法调用

本地方法调用理解起来不难，Java中一些方法的修饰符带有`native`的都是本地方法。相关代码在`com.github.hgaol.reimu.nativee`包中。

这里也是通过一个`Map<String, INativeMethod>`来维护本地方法，其中key为方法的类名方法名描述符构成

```java
String key = className + "~" + methodName + "~" + methodDescriptor;
```

本地方法没有对应的code字段，那如何调用呢？

可以使用预留的`0xFE`操作码来代表本地方法的执行。然后我们在解析类的方法的时候如果是本地方法，我们就进行注入`0xFE`以及返回类型。可以看下`ReClass.Method`的构造函数，这里贴出来

```java
public Method(ReClass clazz, MemberInfo cfField) {
  this.clazz = clazz;
  this.copyMemberInfo(cfField);
  this.copyAttributes(cfField);
  MethodDescriptor md = MethodUtils.parseMethodDescriptor(descriptor);
  this.calcArgsSlotCount(md);
  if (this.isNative()) {
    // 解析类的时候（解析完后会放入方法区，即classMap），会判断如果是本地方法，则手动注入指令码和返回码
    // 执行字节码的时候，解析到调用该方法时，会走入0xfe对应的invoke_native操作中，然后在native_method_pool中查找方法并执行
    // 执行完本地方法(0xfe指令)后，根据返回码执行对应的返回操作
    injectCodeAttribute(md.returnType);
  }
}
```

如果忘了可以看下方法调用，都是得到`resolvedMethod`，然后将其压入栈中，执行其code。相当于获取本地方法的`resolvedMethod`时，会得到code字段为`byte[]{0xfe, 0x<RET_TYPE>}`的`Method`，然后接着`loop`，会走到`invoke_native`的操作码中（可以看下`InvokeNative`），接下来从注册的本地方法中找到该方法并执行。

Java反射很强大，主要是通过类对象来得到该能力。类对象是`java.lang.Class`对象的实例，每个类都有个与之对应的类对象。当使用`String.class`、`new String().getClass()`的时候就会获取到类对象。其中的许多方法都是本地方法，可在`NaClass`和`NaObject`中找到（这里最好使用classpy结合字节码来看，不然容易懵）。

## 9. 异常处理

异常处理需要实现`athrow`指令，同时需要实现`fillInStackTrace`本地方法。

异常处理表是code属性的一部分，记录了方法是否有能力处理某种异常。当异常通过`athrow`抛出后，虚拟机会首先查找当前方法的异常处理表，看他能否处理该异常，如果能则转到相应的字节码开始异常处理，否则看他的调用者的异常处理表，一层一层往上走，直到到达Java虚拟机栈的底部。

异常处理项的`catchType`如果为0，表示`catch-all`，其他则从常量池中返回对应的类引用。

下面看`athrow`指令，首先从操作数栈pop出exception对象，然后查找并将pc设置为异常处理的地方（下次loop则会执行对应字节码，改方法在`Athrow.findAndGotoExceptionHandler`函数），如果当前帧没找到，则pop当前帧，继续往上找。如果虚拟机栈（帧）空了还没找到，打印虚拟机栈信息，但是虚拟机栈信息什么时候设置的呢（对应`Athrow.handleUncaughtException`）？可以看下之前说的`fillInStackTrace`，在调用`athrow`指令的时候，会创建异常类的实例，异常类的祖先类`java.lang.Throwable`会在构造函数中调用`fillInStackTrace()`创建栈信息。
