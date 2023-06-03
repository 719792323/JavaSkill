import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class _4Constructor {
    public static void main(String[] args) {
        User3 user3 = new User3();
        User3 user3_1 = new User3("xx", 11);
        User4 user4 = new User4("xx", 11);
        User4 user4_1 = new User4(11);
    }
}

//空构造函数注解，单独使用没意义
@NoArgsConstructor
//全属性构成函数注解
@AllArgsConstructor
class User3 {
    String name;
    int age;
}

@AllArgsConstructor
//生成一个包含特定参数的构造器，特定参数指的是那些有加上final修饰词的变量
@RequiredArgsConstructor
class User4 {
    String name;
    final int age;
}