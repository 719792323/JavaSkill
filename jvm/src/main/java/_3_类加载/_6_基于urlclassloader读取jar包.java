package _3_类加载;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class _6_基于urlclassloader读取jar包 {
    public static void main(String[] args) {
        try {
            // 指定JAR文件路径
            String jarFilePath = "C:\\Users\\SongJi\\Desktop\\ApacheJMeter.jar";

            // 创建URLClassLoader，指定JAR文件路径
            URL jarUrl = new URL("file://" + jarFilePath);
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl});

            // 获取JAR文件中的所有资源
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entries = jarFile.entries();

            // 读取JAR文件中的每个资源
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                if (!entry.isDirectory() && entry.getName().equals("META-INF/MANIFEST.MF")) {
                    // 获取资源的URL
                    URL resourceUrl = classLoader.getResource(entry.getName());

                    // 读取资源内容
                    if (resourceUrl != null) {
                        System.out.println("File found: " + resourceUrl);
                        InputStream inputStream = resourceUrl.openStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }

                        reader.close();
                    }
                }
            }

            // 关闭资源
            jarFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
