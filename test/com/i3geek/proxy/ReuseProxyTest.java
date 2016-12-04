package com.i3geek.proxy;

import com.i3geek.proxy.base.HelloWorld;
import com.i3geek.proxy.base.HelloWorldImpl;
import com.i3geek.proxy.reuseproxy.HelloInvocationHandler;
import com.i3geek.proxy.reuseproxy.InvocationHandler;
import com.i3geek.proxy.reuseproxy.ReuseProxy;

public class ReuseProxyTest {
    public static void main(String[] args) throws Exception
    {    
        long start = System.currentTimeMillis();
        HelloWorld helloWorldImpl = new HelloWorldImpl();
        InvocationHandler ih = new HelloInvocationHandler(helloWorldImpl);
        HelloWorld helloWorld = (HelloWorld)ReuseProxy.newProxyInstance(HelloWorld.class, ih);
        System.out.println("动态生成代理耗时：" + (System.currentTimeMillis() - start) + "ms");
        helloWorld.print();
        System.out.println();
    }
}
