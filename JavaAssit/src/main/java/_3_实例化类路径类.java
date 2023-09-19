import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

public class _3_实例化类路径类 {
    public static void main(String[] args) throws Exception {
        //实例化out文件夹下的person类
        ClassPool pool = ClassPool.getDefault();
        //指定扫描类路径
        pool.insertClassPath("JavaAssit/out/class");
        //获取类对象
        CtClass personClass = pool.get("com.test.Person");
        //实例化
        Object person = personClass.toClass().newInstance();
        //反射调用
        person.getClass().getMethod("setAge",int.class).invoke(person, 666);
        System.out.println(person.getClass().getMethod("getAge").invoke(person));
    }
}
