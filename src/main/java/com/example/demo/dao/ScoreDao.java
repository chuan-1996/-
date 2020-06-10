package com.example.demo.dao;

import com.example.demo.entity.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author qq491
 */
public interface ScoreDao extends JpaRepository<ScoreEntity, Integer > {

    @Query(nativeQuery = true, value = "select score from information.score where test_index = ?1 and user_id = ?2")
    Object find(int testIndex,String userId);

    @Query(nativeQuery = true, value = "select * from information.score where user_id = ?1")
    List<ScoreEntity> findAllScore(String userId);
}
