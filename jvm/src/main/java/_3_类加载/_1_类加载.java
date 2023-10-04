package _3_类加载;

import java.util.Random;

/**
 * 加载:
 * 1. 通过一个类的全限定名获取定义此类的二进制字节流
 * 2. 将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构
 * 3. 在内存中生成一个代表这个类的java.lang.class对象，作为方法区这个类的各种数据的访问入口
 */
public class _1_类加载 {
    //静态代码块的执行也在initlalization进行
    //a变量定义在static代码块后面不报错且结果a=3，说明prepare阶段已经分配了内存
    //且initlalization赋值顺序和代码顺序相同
    static {
        a = 5;
    }

    //如下a，b是类变量
    static int a = 3;//prepare阶段只是为变量分配内存数值类型变量则是零值，如果是对象则是null，赋值操作在initlalization阶段进行
    final static int b = 3;//b会在编译阶段赋值，因为由final和static共同修饰后相当于是已赋值常量。注意是final static共同修饰的变量才有这种特性
    //如下random，c是实例变量，因为不是static
    //实例变量不会在prepare分配内存，类变量在方法区，实例变量在堆区
    Random random = new Random();
    int c = 3;


    public static void main(String[] args) {
        System.out.println(a);
    }
}
