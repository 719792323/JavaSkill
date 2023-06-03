import lombok.ToString;

public class _2ToString {
    public static void main(String[] args) {
        User user = new User();
        user.id=1;
        user.name="xx";
        System.out.println(user);
    }
}

@ToString
class User{
    int id;
    String name;


}
