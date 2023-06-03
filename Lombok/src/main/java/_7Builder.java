import lombok.Builder;
import lombok.ToString;

public class _7Builder {
    public static void main(String[] args) {
        User8 user = User8.builder().age(1).name("xx").build();
        System.out.println(user);
    }
}

/**
 * 流式构造对象
 */
@Builder
@ToString
class User8{
    int age;
    String name;
}