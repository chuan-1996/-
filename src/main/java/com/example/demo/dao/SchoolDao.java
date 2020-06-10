package com.example.demo.dao;

import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author qq491

 */
public interface SchoolDao extends JpaRepository<SchoolEntity, String > {

}
