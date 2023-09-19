import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Class<? extends Student>> classes = Arrays.asList(StudentA.class, StudentB.class);
        for (Class<? extends Student> aClass : classes) {
            MyAnnotation annotation = aClass.getAnnotation(MyAnnotation.class);
            Student student = aClass.getDeclaredConstructor(String.class, int.class).newInstance(annotation.name(), annotation.age());
            System.out.println(student);
        }
    }
}

class Student {
    final String name;
    final int age;

    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("name:%s,age:%s", name, age);
    }
}

@MyAnnotation(name = "hxx", age = 10)
class StudentA extends Student {

    StudentA(String name, int age) {
        super(name, age);
    }
}

@MyAnnotation(name = "liu")
class StudentB extends Student {

    StudentB(String name, int age) {
        super(name, age);
    }
}