package _3_类加载;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;

//https://blog.51cto.com/u_16099331/6422622
public class _5_classPath下的内容 {
    public static void main(String[] args) throws IOException {
        //getResource能获得对应classpath下的文件
        //my.properties因为在指定的一个classpath下即D:\Code\Java\JavaSkill\jvm\target\classes所以
        //使用对应的classloader能拿到该文件
        //classPath下的一个独有文件
        URL url = ClassLoader.getSystemResource("my.properties");
        System.out.println(url.getFile());
        //获取每个jar包都会有的META-INF/MANIFEST.MF这个文件
        URL url2 = ClassLoader.getSystemResource("META-INF/MANIFEST.MF");
        //为什么只输出了charsets的
        //因为getSystemResource返回的是第一个被加载到META-INF
        System.out.println(url2.getFile());//file:/C:/Program%20Files/Java/jdk1.8.0_221/jre/lib/charsets.jar!/META-INF/MANIFEST.MF
        System.out.println(new File(url2.getFile()).exists());
        //看不同的classloader是否有my.properties这个文件
        ClassLoader loader1 = ClassLoader.getSystemClassLoader();
        System.out.println(loader1);
        System.out.println(loader1.getResource("my.properties").getFile());
        ClassLoader loader2 = loader1.getParent();
        System.out.println(loader2);
        System.out.println(loader2.getResource("my.properties"));

        //查看所有的META-INF
        Enumeration<URL> resources = loader1.getResources("META-INF/MANIFEST.MF");
        while (resources.hasMoreElements()) {
            URL urls = resources.nextElement();
            //读取这个urls下的MANIFEST.MF文件
            InputStream inputStream = urls.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            System.out.println("===============");
        }

    }

}

