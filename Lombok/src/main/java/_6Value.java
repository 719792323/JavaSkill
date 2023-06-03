import lombok.Value;

public class _6Value {
    public static void main(String[] args) {

    }
}

/**
 * @Value =
 * @Getter (注意没有setter)
 * @ToString
 * @EqualsAndHashCode
 * @RequiredArgsConstructor
 */
@Value
class User7{
    int age;
    String name;
}