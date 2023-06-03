import lombok.Getter;
import lombok.Setter;

public class _1GetAndSetter {

    public static void main(String[] args) {
        A a = new A();
        //直接使用getter、setter方法
        a.setA(1);
        System.out.println(a.getA());
    }
}
//相关注解
@Getter
@Setter
class A{
    private int a;
}
