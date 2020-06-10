package com.example.demo.dao;

import com.example.demo.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author qq491

 */
public interface QuestionDao extends JpaRepository<QuestionEntity, Integer > {

    /**
     * 返回本章（章节编号）下的所有问题答案
     */
    @Query(nativeQuery = true, value = "select answer from information.question where `index` = ?1")
    String[] find(int testIndex);

}
