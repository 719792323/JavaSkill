import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.*;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class MyAttachAgent {
    static final ClassPool classPool = ClassPool.getDefault();

    public static void agentmain(String agentOps, Instrumentation inst) throws Exception {
        System.out.println("====agentmain 方法执行");
        System.out.println("agentOps:" + agentOps);
        System.out.println("inst:" + inst);
        System.out.println("====agentmain 方法执行2");
        // 通过Instrumentation 获取到加载的所有类的信息。
        Class[] classes = inst.getAllLoadedClasses();
        System.out.println(Arrays.toString(classes));
        //attach对方法增强和agent写法不一样
        for (Class aClass : classes) {
            if (aClass.getName().contains("RunningProgram")) {
                classPool.importPackage("inner");
                CtClass ctClass = classPool.get(aClass.getName());
                CtMethod running = ctClass.getDeclaredMethod("running");
                running.insertBefore("new InnerClass().echo();System.out.println(\"attach 代理:\"+System.currentTimeMillis());");
                ClassDefinition classDefinition = new ClassDefinition(aClass, ctClass.toBytecode());
                inst.redefineClasses(classDefinition);
            }
        }
    }
}
