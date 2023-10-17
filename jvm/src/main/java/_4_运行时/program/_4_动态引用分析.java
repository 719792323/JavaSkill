package _4_运行时.program;

public class _4_动态引用分析 {
    public void funA() {
        System.out.println("funA");
    }

    /*
    invokevirtual指令调用方法，#5是方法的常量值替换，动态链接就是指向运行时常量池的方法引用（注意是方法引用）
    即#5是，#4也是

    0 aload_0
     1 invokevirtual #5 <_4_运行时/program/_4_动态引用分析.funA : ()V>
     4 getstatic #2 <java/lang/System.out : Ljava/io/PrintStream;>
     7 ldc #6 <funB>
     9 invokevirtual #4 <java/io/PrintStream.println : (Ljava/lang/String;)V>
    12 return
     */
    public void funB() {
        funA();
        System.out.println("funB");
    }

}
