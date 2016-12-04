package com.i3geek.proxy.staticproxy;

import com.i3geek.proxy.base.HelloWorld;
import com.i3geek.proxy.base.HelloWorldImpl;

public class StaticProxyTest {
    public static void main(String args[]){
        HelloWorld helloWorld = new HelloWorldImpl();//真实类
        StaticProxy proxy = new StaticProxy(helloWorld);//生成被代理类
        proxy.print();//执行代理方法
    }
}
