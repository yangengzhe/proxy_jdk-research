> 本项目主要目的是自己实现一个java.lang.reflect.Proxy类。通过三个实例，由简到繁介绍了Proxy用到的技术，并最终实现了一个类似java.lang.reflect.Proxy类的类。
> 本项目初衷供学习代理、动态代理、AOP等知识所用，并不用于真实项目中（因为效率低于至少2个数量级）

## 目的 —— 实现JDK动态代理类

写一个类java.lang.reflect.Proxy的Proxy类，功能和其一样：传入接口、代理内容 -》生成代理

## 主要技术

1. 动态编译技术（本文用JAVA自带的JavaCompiler类，也可以用CGLIB、ASM等字节码增强技术）
	Spring中的动态代理主要由两种，JDK动态代理（本文介绍的）和CGLIB动态代理
2. 反射
3. IO流
4. 对于ClassLoader的理解

## 项目结构

	/src 源码目录
		com.i3geek.proxy.base 	基础类包
	
	/docs 说明文档

## TAGS

- READY	准备工作
- STATICPROXY 静态代理
- AUTOSTATICPROXY 动态生成静态代理



*By I3geek.com* 