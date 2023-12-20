package mybatis;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class MybatisAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        instrumentation.addTransformer(new MybatisSlowSqlTransformer());
    }
}

class MybatisSlowSqlTransformer implements ClassFileTransformer {
    public static final ClassPool CLASS_POOL = ClassPool.getDefault();
    public static final String targetClass = "org.apache.ibatis.executor.statement.PreparedStatementHandler";
    public static final String methodName = "query";

    /**
     * 字节码加载到虚拟机前会进入这个方法
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.replaceAll("/", ".").equals(targetClass)) {
            try {
                CtClass ctClass = CLASS_POOL.get(targetClass);
                CtMethod method = ctClass.getDeclaredMethod(methodName);
                method.insertBefore("mybatis.SlowSQL.start($0.boundSql.getSql(),$1);");
                method.insertAfter("mybatis.SlowSQL.end();");
                return ctClass.toBytecode();
            } catch (Exception e) {
                System.out.println(e);
                throw new RuntimeException("failed to edit class", e);
            }
        }
        return new byte[0];
    }
}


