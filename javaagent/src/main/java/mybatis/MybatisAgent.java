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
    private static final ClassPool CLASS_POOL = ClassPool.getDefault();
    private static final String targetClass = "org.apache.ibatis.executor.statement.PreparedStatementHandler";
    private static final String methodName = "query";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.replaceAll("/", ".").equals(targetClass)) {
            try {
                CtClass ctClass = CLASS_POOL.get(targetClass);
                CtMethod method = ctClass.getDeclaredMethod(methodName);
//                method.insertBefore("String sql=((ClientPreparedStatement) ((HikariProxyPreparedStatement) ((PreparedStatementLogger) ((Proxy) statement).h).statement).delegate).asSql();"
//                        + "System.out.println(\"sql:\"+sql);"
//                        + "System.out.println(\"startTime:\"+System.currentTimeMillis());");
                method.insertBefore("System.out.println(statement)");
//                method.insertBefore("System.out.println(\"startTime:\"+System.currentTimeMillis());");
                method.insertAfter("System.out.println(\"endTime:\"+System.currentTimeMillis());");
                return ctClass.toBytecode();
            } catch (Exception e) {
                throw new RuntimeException("failed to edit class", e);
            }

        }
        return classfileBuffer;
    }
}
