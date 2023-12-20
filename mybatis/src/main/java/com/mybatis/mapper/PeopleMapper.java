package com.mybatis.mapper;

import com.mybatis.pojo.People;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PeopleMapper {

    List<People> getAllPeople();
}
