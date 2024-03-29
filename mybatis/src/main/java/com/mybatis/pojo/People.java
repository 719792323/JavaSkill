package com.mybatis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class People {
    private Integer id;
    private String name;
    private Integer age;

    public People(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
