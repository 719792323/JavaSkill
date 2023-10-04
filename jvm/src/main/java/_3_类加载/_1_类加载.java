package _3_类加载;

public class _1_类加载 {
    public static void main(String[] args) {
        //如下输出结果可以说明，main运行时还没有加载Test类或者没有执行到Test的initlazation阶段
        //只有当调用了new Test（）之后才完全加载
        //并且static对应的cinit代码块只会被执行一次
        Runnable runnable=()->{
            System.out.println("thread start");
            new Test();
            System.out.println("thread end");
        };
        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}

class Test{
    static {
        System.out.println("执行一次static代码块");
    }
}