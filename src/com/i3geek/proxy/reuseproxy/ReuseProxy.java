package com.i3geek.proxy.reuseproxy;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

public class ReuseProxy {
    public static Object newProxyInstance(Class<?> interfaces, InvocationHandler h) throws Exception
    {
        Method[] methods = interfaces.getMethods();        
        StringBuilder sb = new StringBuilder(1024);
        //生成$Proxy1代理类（因为JDK也是叫$Proxy1）
        sb.append("package com.i3geek.proxy.reuseproxy;\n\n");
        sb.append("import java.lang.reflect.Method;\n\n");
        sb.append("import com.i3geek.proxy.base.HelloWorld;\n");
        sb.append("public class $Proxy1 implements " +  interfaces.getSimpleName() + "\n");
        sb.append("{\n");
        sb.append("\tInvocationHandler h;\n\n");
        sb.append("\tpublic $Proxy1(InvocationHandler h)\n");
        sb.append("\t{\n");
        sb.append("\t\tthis.h = h;\n");
        sb.append("\t}\n\n");
        for (Method m : methods)
        {
            sb.append("\tpublic " + m.getReturnType() + " " + m.getName() + "()\n");
            sb.append("\t{\n");
            sb.append("\t\ttry\n");
            sb.append("\t\t{\n");
            sb.append("\t\t\tMethod md = " + interfaces.getName() + ".class.getMethod(\"" + m.getName() + "\");\n");
            sb.append("\t\t\th.invoke(this, md);\n");
            sb.append("\t\t}\n");
            sb.append("\t\tcatch (Exception e)\n");
            sb.append("\t\t{\n");
            sb.append("\t\t\te.printStackTrace();\n");
            sb.append("\t\t}\n");
            sb.append("\t}\n");
        }
        sb.append("}");
        
        //生成.java源文件
        String fileDir = System.getProperty("user.dir");
        String fileName = fileDir + "/src/com/i3geek/proxy/reuseproxy/$Proxy1.java";
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
        URL[] urls = new URL[] {(new URL("file:\\" + System.getProperty("user.dir") + "/src"))};
        URLClassLoader ul = new URLClassLoader(urls);
        Class<?> c = ul.loadClass("com.i3geek.proxy.reuseproxy.$Proxy1");
 
        //利用反射将c实例化出来 构造器初始化 利用封装好的InvocationHandler
        Constructor<?> constructor = c.getConstructor(InvocationHandler.class);
        Object obj = constructor.newInstance(h);
 
       //使用完毕删除生成的代理.java文件和.class文件，这样就看不到动态生成的内容了
        File classFile = new File(fileDir + "/src/com/i3geek/proxy/reuseproxy/$Proxy1.class");
        javaFile.delete();
        classFile.delete();
 
        return obj;
    }
}
