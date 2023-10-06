package _3_类加载;

public class _3_双亲委派加载 {
    public static void main(String[] args) {
        //发现加载的并不是自己定义java.lang.String因为由于双亲委派机制
        //bootstrap已经加载了该类，所以不会使用到自定义的String
        java.lang.String str = new java.lang.String();
        System.out.println(str.getClass().getClassLoader());//null
    }
}
