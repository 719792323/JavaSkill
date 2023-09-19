package sj.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import sj.agent.inner.InnerClass;

public class Hello {
    class Person {
        int age;

        String name;

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public Person() {
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public void echo() {
        System.out.println("this is hello world");
        InnerClass.echo();
        Person person = new Person(10, "sj");
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(person));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
