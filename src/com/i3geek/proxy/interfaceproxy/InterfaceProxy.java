package com.i3geek.proxy.interfaceproxy;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.i3geek.proxy.base.HelloWorldImpl;

public class InterfaceProxy {
    public static Object newProxyInstance(Class<?> interfaces) throws Exception
    {
        //反射获得接口的方法，构造java源文件代码
        Method[] methods = interfaces.getMethods();
        StringBuilder sb = new StringBuilder(700);
        sb.append("package com.i3geek.proxy.interfaceproxy;\n\n");
        sb.append("import com.i3geek.proxy.base.HelloWorld;\n");
        sb.append("public class StaticProxy implements " +  interfaces.getSimpleName() + "\n");
        sb.append("{\n");
        sb.append("\t" + interfaces.getSimpleName() + " interfaces;\n\n");
        sb.append("\tpublic StaticProxy(" + interfaces.getSimpleName() +  " interfaces)\n");
        sb.append("\t{\n");
        sb.append("\t\tthis.interfaces = interfaces;\n");
        sb.append("\t}\n\n");
        for (Method m : methods)
        {
            sb.append("\tpublic " + m.getReturnType() + " " + m.getName() + "()\n");
            sb.append("\t{\n");
            sb.append("\t\tSystem.out.println(\"Before Hello World!\");\n");
            sb.append("\t\tinterfaces." + m.getName() + "();\n");
            sb.append("\t\tSystem.out.println(\"After Hello World!\");\n");
            sb.append("\t}\n");
        }
        sb.append("}");
        
        //生成.java源文件
        String fileDir = System.getProperty("user.dir");
        String fileName = fileDir + "/src/com/i3geek/proxy/interfaceproxy/StaticProxy.java";
        File javaFile = new File(fileName);
        Writer writer = new FileWriter(javaFile);
        writer.write(sb.toString());
        writer.close();
 
        //动态编译.Java代码,生成.class文件
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager sjfm = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> iter = sjfm.getJavaFileObjects(fileName);
        CompilationTask ct = compiler.getTask(null, sjfm, null, null, null, iter);
        ct.call();
        sjfm.close();
 
        //将生成的.class文件载入内存，默认的ClassLoader只能载入CLASSPATH下的.class文件，由于本例从文件中读 故用URLClassLoader
        URL[] urls = new URL[] {(new URL("file:\\" + System.getProperty("user.dir") + "/src"))};//可以从网络中读取(HTTP)，也可以从本地(FILE)
        URLClassLoader ul = new URLClassLoader(urls);
        Class<?> c = ul.loadClass("com.i3geek.proxy.interfaceproxy.StaticProxy");
        
        //利用反射将c实例化出来 构造器初始化
        /** 需要实例化被代理类 */
        Constructor<?> constructor = c.getConstructor(interfaces); // 传入的参数
        Object helloWorldImpl = new HelloWorldImpl();
        Object obj = constructor.newInstance(helloWorldImpl);
 
        //使用完毕删除生成的代理.java文件和.class文件，这样就看不到动态生成的内容了
        File classFile = new File(fileDir + "/src/com/i3geek/proxy/interfaceproxy/StaticProxy.class");
        javaFile.delete();
        classFile.delete();
 
        return obj;
    }
}
