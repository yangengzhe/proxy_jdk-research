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

## 实现步骤

		`0.准备工作`						`1.版本0（手动实现）`
	Interface HelloWorld-------------------------|
			↑			 `3.版本2（参数传接口）`	 ↓
			↑			    					Proxy  `2.版本1（动态生成）`
	    	↑				 					 ↑
	Class HelloWorldImpl-------------------------|
			↓									 |
			↓ 封装实体类 + 增强					 |
			↓									 |
	Class HelloInvocationHandler				 |     `4.版本3（参数传封装实例）`
			↓									 |
			↓									 |
			↓									 |
	Interface InvocationHandler------------------|

- 0.准备工作：定义接口和被代理类（真实类）
- 1.版本0：手动实现静态代理类 StaticProxy
- 2.版本1：动态生成静态代理类 AutoStaticProxy
- 3.版本2：参数传入接口，可以使动态代理类不指定接口，可以为任意接口生成代理类 InterfaceProxy
- 4.版本3：参数传入接口和封装类，可以使动态代理类不指定接口和实现类以及代理方法，可以为任意接口生成任何增强的代理类 REUSEPROXY

## 项目结构

	/src 源码目录
		com.i3geek.proxy.base 	基础类包
		staticproxy				1手动静态代理类包
		auto_staticproxy		2自动生成静态代理类包
		interfaceproxy			3不指定接口动态生成代理类包
		reuseproxy				4可复用代理内容的代理类包
	/test 测试目录
	/docs 说明文档

## TAGS

- READY	准备工作
- STATICPROXY 静态代理
- AUTOSTATICPROXY 动态生成静态代理
- INTERFACEPROXY 不指定接口动态生成代理类
- REUSEPROXY 可复用代理内容的代理类


*By I3geek.com* 