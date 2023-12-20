package sj.agent;

import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/*
agent只能依附于别的jar运行，不能独立运行
如有个foo.jar,那么运行方式为
java -jar foo.jar -javaagent:myagent.jar
 */
//https://zhuanlan.zhihu.com/p/476317425;
public class _1_javaagent {
    static final ClassPool classPool = ClassPool.getDefault();

    /**
     * agent的方法入口不是main而实premain
     *
     * @param args
     */
    public static void premain(String args, Instrumentation instrumentation) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
        //-javaagent:D:\Code\Java\JavaSkill\javaagent\target\javaagent-1.0-SNAPSHOT.jar=thisisagentargs
        //注意=后面的参数不能由空格，即不能写成=this is agent args
        System.out.println("this is myagent");
        System.out.println("agent args:" + args);
        System.out.println("========");
        //修改字节码，记入方法开始时间
        instrumentation.addTransformer(new ClassFileTransformer() {
            //className默认用/进行分割,一般需要用.进行替换处理
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {//判断当前类是不是要修改的类
                System.out.println(className);
                if (className.equals("AgentTest")) {
                    try {
                        CtClass ctClass = classPool.get(className.replaceAll("/", "."));
                        CtMethod foo = ctClass.getDeclaredMethod("foo");
                        // 引入Hello类所在的包
                        classPool.importPackage("sj.agent");
                        //插入代码
                        foo.insertBefore("new Hello().echo();System.out.println(System.currentTimeMillis());");
                        return ctClass.toBytecode();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        });
    }
}
