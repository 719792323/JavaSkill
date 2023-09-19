import javassist.*;

/**
 * 给一个已知的类进行代理增强
 */
public class _2_类修改_切面 {
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass personService = pool.get("PersonService");
        // 获取目标类的方法
        CtMethod fly = personService.getDeclaredMethod("fly");
        // 在方法执行前插入代码
        fly.insertBefore("System.out.println(\"起飞前准备降落伞\");");
        // 在方法执行后插入代码
        fly.insertAfter("System.out.println(\"成功落地\");");
        CtMethod joinFriend = new CtMethod(CtClass.voidType, "joinFriend", new CtClass[]{}, personService);
        joinFriend.setModifiers(Modifier.PUBLIC);
        // 设置方法的方法体
        joinFriend.setBody("{System.out.println(\"加个好友吧\");}");
        personService.addMethod(joinFriend);
        //实例化
        Object instance = personService.toClass().newInstance();
        System.out.println(instance instanceof PersonService);
        //这样只能调用到fly
        PersonService service = (PersonService) instance;
        service.fly();
        //调用方法二
        instance.getClass().getMethod("fly").invoke(instance);
        instance.getClass().getMethod("joinFriend").invoke(instance);
    }
}
