
package _2_生命周期;

/**
 * Java虚拟机的启动是通过引导类加载器(bootstrap class loader)创建一个初始类(initial class)来完成的,这个类是由虚拟机的具体实现指定的。
 */
public class Main {
    public static void main(String[] args) {
        /*
        程序正常执行结束
        程序在执行过程中遇到了异常或错误而异常终止
        由于操作系统出现错误而导致Java虚拟机进程终止
        某线程调用Runtime类或System类的exit方法，或Runtime类的halt方法，并且Java安全管理器也允许这次exit或halt操作。
         */
//        System.exit(0);
//        Runtime.getRuntime().exit(0);
//        Runtime.getRuntime().halt(0);

    }
}

