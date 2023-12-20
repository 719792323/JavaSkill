package com.mybatis;

import com.mybatis.mapper.PeopleMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MyBatisApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MyBatisApplication.class, args);
    }
}
