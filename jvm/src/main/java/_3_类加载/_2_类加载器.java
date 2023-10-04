package _3_类加载;

import com.sun.java.accessibility.AccessBridge;
import sun.misc.Launcher;

import java.net.URL;
import java.util.ArrayList;

public class _2_类加载器 {
    public static void main(String[] args) {
        //获取系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        //获取扩展类加载器
        ClassLoader extendClassLoader = systemClassLoader.getParent();
        //获取启动类加载器(获取不到，返回值是null)
        ClassLoader bootstrapClassLoader = extendClassLoader.getParent();

        //查看某具体类是由什么类加载器加载
        System.out.println(_2_类加载器.class.getClassLoader());//AppClassLoader 系统类加载器
        System.out.println(AccessBridge.class.getClassLoader());//ExtClassLoader 扩展类加载器
        System.out.println(ArrayList.class.getClassLoader());//null 说明是bootstrap类加载器

        //获取boostrap加载器加载的类urls
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL.toExternalForm());
        }
        System.out.println("-------");
        //扩展类加载器加载路径
        String property = System.getProperty("java.ext.dirs");
        System.out.println(property);


    }
}
