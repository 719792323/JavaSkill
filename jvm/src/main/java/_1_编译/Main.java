package _1_编译;

/*
运行并生产class文件，进入该class文件目录执行反编译命令:javap -v Main.class
Classfile /D:/Code/Java/JavaSkill/jvm/target/classes/_1_编译/Main.class
  Last modified 2023-10-1; size 441 bytes
  MD5 checksum db19e176b2f22df9f4207730dc2904f8
  Compiled from "Main.java"
public class _1_编译.Main
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #3.#21         // java/lang/Object."<init>":()V
   #2 = Class              #22            // _1_编译/Main
   #3 = Class              #23            // java/lang/Object
   #4 = Utf8               <init>
   #5 = Utf8               ()V
   #6 = Utf8               Code
   #7 = Utf8               LineNumberTable
   #8 = Utf8               LocalVariableTable
   #9 = Utf8               this
  #10 = Utf8               L_1_编译/Main;
  #11 = Utf8               main
  #12 = Utf8               ([Ljava/lang/String;)V
  #13 = Utf8               args
  #14 = Utf8               [Ljava/lang/String;
  #15 = Utf8               a
  #16 = Utf8               I
  #17 = Utf8               b
  #18 = Utf8               c
  #19 = Utf8               SourceFile
  #20 = Utf8               Main.java
  #21 = NameAndType        #4:#5          // "<init>":()V
  #22 = Utf8               _1_编译/Main
  #23 = Utf8               java/lang/Object
{
  public _1_编译.Main();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 3: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   L_1_编译/Main;
    //main方法
  public static void main(java.lang.String[]);//方法声明
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=4, args_size=1
         0: iconst_1 //定义变量a
         1: istore_1 //存储变量a
         2: iconst_2
         3: istore_2
         4: iload_1 //读取变量a
         5: iload_2 //读取变量b
         6: iadd //加
         7: istore_3 //存储变量c
         8: return //返回语句
      LineNumberTable:
        line 5: 0
        line 6: 2
        line 7: 4
        line 8: 8
      //变量表
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       9     0  args   [Ljava/lang/String;
            2       7     1     a   I
            4       5     2     b   I
            8       1     3     c   I
}
SourceFile: "Main.java"
 */
public class Main {
    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        int c = a + b;
    }
}
