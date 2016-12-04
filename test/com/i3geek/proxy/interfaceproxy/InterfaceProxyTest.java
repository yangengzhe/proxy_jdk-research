package com.i3geek.proxy.interfaceproxy;

import com.i3geek.proxy.base.HelloWorld;

public class InterfaceProxyTest {
    public static void main(String[] args) throws Exception
    {    
        long start = System.currentTimeMillis();
        HelloWorld helloWorld = (HelloWorld)InterfaceProxy.newProxyInstance(HelloWorld.class);
        System.out.println("动态生成代理耗时：" + (System.currentTimeMillis() - start) + "ms");
        helloWorld.print();
        System.out.println();
    }
}
