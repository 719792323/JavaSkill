import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.reflect.Method;
import java.util.Iterator;

public class test {
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath("D:\\Code\\Java\\JavaSkill\\javaagent\\target\\javaagent-1.0-SNAPSHOT.jar");
//        CtClass ctClass = pool.get("sj.agent.Hello");
//        Class<?> aClass = ctClass.toClass();
//        Method echo = aClass.getDeclaredMethod("echo");
//        Object instance = aClass.newInstance();
//        echo.invoke(instance);
//        Iterator<String> importedPackages = pool.getImportedPackages();
        pool.importPackage("sj.agent");
        CtClass ctClass = pool.getCtClass("PersonService");
        CtMethod fly = ctClass.getDeclaredMethod("fly");
        fly.insertBefore("new Hello().echo();");
        ctClass.writeFile("JavaAssit/out/class");
//        Object instance = ctClass.toClass().newInstance();
//        PersonService.class.getDeclaredMethod("fly").invoke(instance);

    }
}
