package com.i3geek.proxy.staticproxy;

import com.i3geek.proxy.base.HelloWorld;


public class StaticProxy implements HelloWorld {

    private HelloWorld helloWorld;
    
    public StaticProxy(HelloWorld helloWorld)
    {
        this.helloWorld = helloWorld;
    }
 
    public void print()
    {
        System.out.println("Before Hello World!");
        helloWorld.print();
        System.out.println("After Hello World!");
    }

}
