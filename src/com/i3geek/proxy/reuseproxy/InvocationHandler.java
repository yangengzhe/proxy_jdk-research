package com.i3geek.proxy.reuseproxy;

import java.lang.reflect.Method;

public interface InvocationHandler {
    void invoke(Object proxy, Method method) throws Exception;
}
