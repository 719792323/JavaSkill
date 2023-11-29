package _3_类加载;

import _3_类加载.Animal;

public class Dog implements Animal {
    @Override
    public void say() {
        System.out.println("this is a dog");
    }

    public static void main(String[] args) {
//        new _3_类加载.Dog()
    }
}
