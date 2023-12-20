package com.mybatis.controller;

import com.mybatis.mapper.PeopleMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PeopleMapperController {
    @Resource
    private PeopleMapper peopleMapper;

    @RequestMapping(value = "/getAllPeople")
    public Object testGetAllPeople() {
        return peopleMapper.getAllPeople();
    }

}
