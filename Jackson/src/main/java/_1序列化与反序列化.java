
import com.fasterxml.jackson.databind.ObjectMapper;

public class _1序列化与反序列化 {
    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = Person.newInstance();
        String json = objectMapper.writeValueAsString(person);
        System.out.println(json);
        Person value = objectMapper.readValue(json, Person.class);
        System.out.println(value);
    }
}
