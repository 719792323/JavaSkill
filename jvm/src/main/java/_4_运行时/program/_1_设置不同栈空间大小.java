package _4_运行时.program;

public class _1_设置不同栈空间大小 {
    static int count = 0;

    //通过设置-Xss参数设置不同jvm的栈大小，看出现栈溢出异常的递归次数
    //默认情况是9905次
    //设置-Xss256k时，最大为1952次
    public static void main(String[] args) {
        System.out.println("count:" + count++);
        main(args);
    }
}
