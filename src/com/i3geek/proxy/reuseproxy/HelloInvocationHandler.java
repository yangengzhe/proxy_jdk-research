package com.i3geek.proxy.reuseproxy;

import java.lang.reflect.Method;


public class HelloInvocationHandler implements InvocationHandler {
    private Object obj;
    
    public HelloInvocationHandler(Object obj)
    {
        this.obj = obj;
    }
 
    public void invoke(Object proxy, Method method)
    {
        System.out.println("Before Hello World!");
        try
        {
            method.invoke(obj, new Object[]{});
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("After Hello World!");
    }

}
