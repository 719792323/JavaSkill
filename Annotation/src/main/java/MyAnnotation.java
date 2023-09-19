import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//注解修饰的对象范围
/*
METHOD：用于描述方法
PACKAGE：用于描述包
PARAMETER：用于描述方法变量
TYPE：用于描述类、接口或enum类型
 */
@Target(ElementType.TYPE)
//注解保留时间
/*
SOURCE：在源文件中有效，编译过程中会被忽略
CLASS：随源文件一起编译在class文件中，运行时忽略
RUNTIME：在运行时有效，只有定义为RetentionPolicy.RUNTIME时，我们才能通过注解反射获取到注解
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String name();
    //设置注解默认值
    int age() default 0;
}
