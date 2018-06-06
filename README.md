# Reimu

这是一个Java实现的简陋的JVM，主要为了巩固学习JVM原理。

看完[《深入理解Java虚拟机（第2版）》](https://book.douban.com/subject/24722612/)和[《自己动手写Java虚拟机》](https://book.douban.com/subject/26802084/)，觉得自己也应该~~抄~~写一个Java虚拟机。一方面巩固所学，一方面深入细节温故知新。

开发过程中需查看`class`字节码，用到了[classpy](https://github.com/zxh0/classpy)，很好用，还顺手改了两个[bug](https://github.com/zxh0/classpy/pulls?q=is%3Apr+is%3Aclosed+author%3Ahgaol) ~

在执行单元测试或者运行时，修改`logback-test.xml` 或者 `logback.xml` 等级为DEBUG，可以输出运行过程中执行的操作码和操作数，以及虚拟机栈的操作数栈和本地变量表的内容变化，便于理解和调试。


# Build

```shell
mvn clean package
```

# Test

```shell
mvn clean test
```

# Run

```shell
# Example
java -jar target/reimu-0.1.0.jar -cp target/test-classes com.github.hgaol.reimu.example.HelloWorld
```

如果觉得上面太长不好看，可以写个简单的小脚本，比如叫作`reimu` ，和`reimu-0.1.0.jar` 放在一起

```shell
#!/usr/bin/env bash
java -jar reimu-0.1.0.jar $@
```

这时候就可以直接`./reimu -cp test-classes com.github.hgaol.reimu.example.HelloWorld`

# Conclusion

觉得还是写一写跑一跑理解的更好，心里更踏实一些~

为什么使用Java实现？因为比较熟悉，主要目的在于理解逻辑，其他阻碍越小越好。

[SUMMARY](https://github.com/hgaol/reimu/blob/master/SUMMARY.md)是在写完代码之后对一些逻辑的梳理，主要是防止时间长了自己都不知道自己写的啥~



## TODO

- [ ] 多线程

