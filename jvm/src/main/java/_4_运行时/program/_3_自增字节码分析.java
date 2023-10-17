package _4_运行时.program;

public class _3_自增字节码分析 {
    /*
     public static void main(String[] args) {
        int i = 0;
        ++i;
        ++i;
        ++i;
        int j = i + i++;
        System.out.println(j);
    }
     0 iconst_0
     1 istore_1
     2 iinc 1 by 1
     5 iinc 1 by 1
     8 iinc 1 by 1
    11 iload_1
    12 iload_1
    13 iinc 1 by 1
    16 iadd
    17 istore_2
    18 getstatic #2 <java/lang/System.out : Ljava/io/PrintStream;>
    21 iload_2
    22 invokevirtual #3 <java/io/PrintStream.println : (I)V>
    25 return

     */
    public static void main(String[] args) {
        int i = 0;
        i++;
        ++i;
        int j = ++i + i++;
        System.out.println(j);
    }
}
