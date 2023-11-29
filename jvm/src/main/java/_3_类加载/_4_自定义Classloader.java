package _3_类加载;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class _4_自定义Classloader {

    public static void main(String[] args) throws Exception {
        String loadPath="D:/Code/Java/JavaSkill/jvm/src/main/java/";
        MyClassloader myClassloader1 = new MyClassloader(loadPath);
        System.out.println(myClassloader1.getParent());
        Class<?> dog1 = myClassloader1.findClass("_3_类加载.Dog");
        Animal animal= (Animal) dog1.newInstance();
        animal.say();
        //同一类加载器，加载的类对象实例是唯一的
        Class<?> dog2 = myClassloader1.loadClass("_3_类加载.Dog");
        System.out.println(dog1==dog2);
        //不同类加载器，加载的相同的类，也会认为不一致
        Class<Dog> dog3 = Dog.class;
        System.out.println(dog3.getClassLoader());
        System.out.println(dog3==dog1);

    }
}

class MyClassloader extends ClassLoader {
    private String loadPath;

    public MyClassloader(String loadPath) {
        this.loadPath = loadPath;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes = null;
        String classFilePath = loadPath + name.replace(".", "/") + ".class";
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             FileInputStream inputStream = new FileInputStream(classFilePath)) {
            byte bytes[] = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            classBytes = outputStream.toByteArray();
            if (classBytes == null || classBytes.length == 0) {
                throw new ClassNotFoundException(String.format("%s not exist or empty", classFilePath));
            }
        } catch (IOException e) {
            throw new ClassNotFoundException(e.getMessage());
        }
        return defineClass(name, classBytes, 0, classBytes.length);
    }
}


