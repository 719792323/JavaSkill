package _4_运行时.program;

import java.util.Random;

public class _2_局部变量表 {
    /*
   public static void method1();
    descriptor: ()V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
    /*
    stack 最大操作数栈，JVM运行时会根据这个值来分配栈帧(Frame)中的操作栈深度,此处为2
    locals: 局部变量所需的存储空间，单位为Slot,Slot是虚拟机为局部变量分配内存时所使用的最小单位，为4个字节大小。方法参数(包括实例方法中的隐藏参数this)，显示异常处理器的参数(try catch中的catch块所定义的异常)，方法体中定义的局部变量都需要使用局部变量表来存放。值得一提的是，locals的大小并不一定等于所有局部变量所占的Slot之和，因为局部变量中的Slot是可以重用的。
    args_size: 方法参数的个数，这里是1，因为每个实例方法都会有一个隐藏参数this
    attribute_info方法体内容，0,1,4为字节码"行号"，该段代码的意思是将第一个引用类型本地变量推送至栈顶，然后执行该类型的实例方法，也就是常量池存放的第一个变量，也就是注释里的"java/lang/Object.“”: ()V", 然后执行返回语句，结束方法。
    LineNumberTable 该属性的作用是描述源码行号与字节码行号(字节码偏移量)之间的对应关系。可以使用 -g:none或-g:lines选项来取消或要求生成这项信息，如果选择不生成LineNumberTable，当程序运行异常时将无法获取到发生异常的源码行号，也无法按照源码
    的行数来调试程序。
    LocalVariableTable 该属性的作用是描述帧栈中局部变量与源码中定义的变量之间的关系。可以使用 -g:none 或-g:vars来取消或生成 这项信息，如果没有生成这项信息，那么当别人引用这个方法时，将无法获取到参数名称，取而代之的是arg0, arg1这样的占位符。 start表示该局部变量在哪一行开始可见，length表示可见行数，Slot代表所在帧栈位置，Name是变量名称，然后是类型签名。
    SourceFile 源码文件名称
    //stack是操作数栈深度
    //locals局部变量表最大深度
      stack=1, locals=2, args_size=0
         0: sipush        666
         3: istore_0
         4: sipush        233
         7: istore_1
         8: return
      LineNumberTable:
        line 29: 0
        line 30: 4
        line 32: 8
      LocalVariableTable: //局部变量表
      //一个slot大小为四个字节，int数据类型会占据一个slot，如果是double会占据两个
      //不足一个slot的也会占据一个slot
      //引用类型也会占据一个slot
        Start  Length  Slot  Name   Signature
            2       3     0     a   I //index类型
            4       1     1     b   I //index类型

     */
    static public void method1() {
        int a, b;
        a = 666;
        b = 233;

    }

    /*
    public static int method2(int);
    descriptor: (I)I
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=2, args_size=1
         0: new           #2                  // class java/util/Random
         3: dup
         4: invokespecial #3                  // Method java/util/Random."<init>":()V
         7: astore_1
         8: iload_0
         9: ireturn
      LineNumberTable:
        line 50: 0
        line 51: 8
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      10     0     a   I //int类型
            8       2     1 random   Ljava/util/Random; L是引用类型


     */
    static public int method2(int a) {
        Random random = new Random();
        return a;
    }

    /*
      public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      //locals=1，表示局部变量表有一个参数，即args
      stack=2, locals=1, args_size=1
         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #3                  // String local variable
         5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: return
      LineNumberTable: //行表把code段和LocalVariableTable关联起来。
      //比如main方法中只有两行代码，即sout和return，但是Code段实际有4行
        line 15: 0
        line 16: 8
      LocalVariableTable:
        Start  Length  Slot//slot是变量槽索引  Name   Signature
            0       9     0  args   [Ljava/lang/String; //局部变量args
     */
    public static void main(String[] args) {
        System.out.println("local variable");
    }
}
