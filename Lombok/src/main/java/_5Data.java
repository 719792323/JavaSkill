import lombok.Data;

public class _5Data {
    public static void main(String[] args) {
        User6 user6 = new User6();
        user6.setAge(1);
        System.out.println(user6.getAge());
        System.out.println(user6);
        User6 user6_1 = new User6();
        user6_1.setAge(1);
        System.out.println(user6.equals(user6_1));
    }
}

/**
 * @Data = 如下注解
 * @Getter/@Setter
 * @ToString
 * @EqualsAndHashCode
 * @RequiredArgsConstructor
 */
@Data
class User6{
    int age;
    String name;
}