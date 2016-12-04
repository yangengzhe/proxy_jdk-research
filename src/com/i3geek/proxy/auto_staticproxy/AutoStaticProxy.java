package com.i3geek.proxy.auto_staticproxy;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.i3geek.proxy.base.HelloWorld;
import com.i3geek.proxy.base.HelloWorldImpl;

public class AutoStaticProxy{
    
    public static Object newProxyInstance() throws Exception
    {
        //编写代理类的代码 
        String src = "package com.i3geek.proxy.auto_staticproxy;\n\n" + 
                     "import com.i3geek.proxy.base.HelloWorld;\n" +
                     "public class StaticProxy implements HelloWorld\n" + 
                     "{\n" + 
                     "\tHelloWorld helloWorld;\n\n" + 
                     "\tpublic StaticProxy(HelloWorld helloWorld)\n" + 
                     "\t{\n" + 
                     "\t\tthis.helloWorld = helloWorld;\n" + 
                     "\t}\n\n" + 
                     "\tpublic void print()\n" + 
                     "\t{\n" + 
                     "\t\tSystem.out.println(\"Before Hello World!\");\n" + 
                     "\t\thelloWorld.print();\n" + 
                     "\t\tSystem.out.println(\"After Hello World!\");\n" + 
                     "\t}\n" + 
                     "}";
 
        //生成.java源文件
        String fileDir = System.getProperty("user.dir");
        String fileName = fileDir + "/src/com/i3geek/proxy/auto_staticproxy/StaticProxy.java";
        File javaFile = new File(fileName);
        Writer writer = new FileWriter(javaFile);
        writer.write(src);
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
        Class<?> c = ul.loadClass("com.i3geek.proxy.auto_staticproxy.StaticProxy");
 
        //利用反射将c实例化出来 构造器初始化
        /** 前提要指定接口和实例类 */
        Constructor<?> constructor = c.getConstructor(HelloWorld.class);
        HelloWorld helloWorldImpl = new HelloWorldImpl();
        HelloWorld helloWorld = (HelloWorld)constructor.newInstance(helloWorldImpl);
 
        //使用完毕删除生成的代理.java文件和.class文件，这样就看不到动态生成的内容了
        File classFile = new File(fileDir + "/src/com/i3geek/proxy/auto_staticproxy/StaticProxy.class");
        javaFile.delete();
        classFile.delete();
 
        return helloWorld;
    }
}
