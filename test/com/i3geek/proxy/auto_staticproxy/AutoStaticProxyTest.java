package com.i3geek.proxy.auto_staticproxy;

import com.i3geek.proxy.base.HelloWorld;

public class AutoStaticProxyTest {
    public static void main(String[] args) throws Exception
    {    
        long start = System.currentTimeMillis();
        HelloWorld helloWorld = (HelloWorld)AutoStaticProxy.newProxyInstance();
        System.out.println("动态生成代理耗时：" + (System.currentTimeMillis() - start) + "ms");
        helloWorld.print();
        System.out.println();        
    }
}
