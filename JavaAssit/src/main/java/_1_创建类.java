import javassist.*;

import java.util.Arrays;
import java.util.Collections;

public class _1_创建类 {
    public static void main(String[] args) throws Exception {
        // 1.创建默认的ClassPool，ClassPool是一个存储CtClass的Hash表
        ClassPool pool = ClassPool.getDefault();
        // 2. 新建一个空类，叫Person类
        CtClass person = pool.makeClass("com.test.Person");
        // 3.新增一个字段 private String name；
        CtField name = new CtField(pool.get("java.lang.String"), "name", person);
        // 4.设置该字段为私有
        name.setModifiers(Modifier.PRIVATE);
        // 5.另外一种创建属性的写法
        CtField age = CtField.make("private int age;", person);
        // 6.给字段进行默认初始化，并添加到Person类中
        person.addField(name, CtField.Initializer.constant("小明"));
        person.addField(age, CtField.Initializer.constant(20));
        // 7.生成getter、setter方法
        person.addMethod(CtNewMethod.setter("setName", name));
        person.addMethod(CtNewMethod.getter("getName", name));
        person.addMethod(CtNewMethod.setter("setAge", age));
        person.addMethod(CtNewMethod.getter("getAge", age));

        // 8.添加无参数的构造方法
        CtConstructor noArgsConstructor = new CtConstructor(new CtClass[]{}, person);
        // 设置构造方法的方法体
        noArgsConstructor.setBody("{name=\"无参数构造方法\";age=0;}");
        // 添加到person类中
        person.addConstructor(noArgsConstructor);
        // 9.设置有参构造方法
        CtConstructor fullArgsConstructor = new CtConstructor(new CtClass[]{pool.get("java.lang.String"), CtClass.intType}, person);
        // 设置构造方法的方法体
        // $0=this, $1,$2,$3...代表第几个参数
        fullArgsConstructor.setBody("{$0.name=$1;$0.age=$2;}");
        person.addConstructor(fullArgsConstructor);

        // 10.创建一个名为printName的方法，无参数，无返回值，输出name值
        CtMethod printName = new CtMethod(CtClass.voidType, "printName", new CtClass[]{}, person);
        // 设置方法访问类型
        printName.setModifiers(Modifier.PUBLIC);
        printName.setBody("{System.out.println(name);}");
        // 上面的方式可以换成这种快速创建
        CtMethod printAge = CtMethod.make("public void printAge(){System.out.println(age);}", person);

        // 添加到person类中
        person.addMethod(printName);
        person.addMethod(printAge);

        // 11.将创建的类对象编译成.class文件，输出到指定到路径为当前路径下的out/class路径
        person.writeFile("JavaAssit/target/out/class/");

        /**
         * 在 Javassist 中，类 Javaassit.CtClass 表示 class 文件。
         * 一个 GtClass (编译时类）对象可以处理一个 class 文件，ClassPool是 CtClass 对象的容器。
         * 它按需读取类文件来构造 CtClass 对象，并且保存 CtClass 对象以便以后使用。
         * 需要注意的是 ClassPool 会在内存中维护所有被它创建过的 CtClass，当 CtClass 数量过多时，会占用大量的内存，
         * API中给出的解决方案是有意识的调用CtClass的detach()方法以释放内存。
         */

    }
}
