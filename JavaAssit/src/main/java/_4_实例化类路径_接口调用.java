import javassist.ClassPool;
import javassist.CtClass;

public class _4_实例化类路径_接口调用 {
    public static void main(String[] args) throws Exception {
        /*
        在案例三中，实例化后的person的方法都是通过反射调用的，这不仅麻烦且耗费性能。
        如果是已知需要调用的方法集合，可以考虑为该类生成一个接口类。这样在newInstance()的时候我们就可以强转为接口，可以将反射的那一套省略掉了。
         */
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath("JavaAssit/out/class");
        CtClass personClass = pool.get("com.test.Person");
        //获取接口
        CtClass personInterface = pool.get("PersonInterface");
        personClass.setInterfaces(new CtClass[]{personInterface});
        PersonInterface personInstance= (PersonInterface) personClass.toClass().newInstance();
        personInstance.setAge(666);
        System.out.println(personInstance.getAge());
    }
}
