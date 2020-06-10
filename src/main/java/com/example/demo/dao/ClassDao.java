package com.example.demo.dao;

import com.example.demo.entity.ClasssEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author qq491

 */
public interface ClassDao extends JpaRepository<ClasssEntity, String > {


    @Query(nativeQuery = true, value = "select class_name from information.classs where classs.school_name = ?1")
    List<String> find1(String school);
}


