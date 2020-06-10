package com.example.demo.dao;

import com.example.demo.entity.TestEntity;
import com.example.demo.entity.TestName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author qq491
 * table test
 * test_index test_name
 * 章节编号 章节名称
 */


public interface TestDao extends JpaRepository<TestEntity, Integer > {

    /**
     * 查找一个TestEntity实体 注意 其下的问题将不会获得
     */
    @Query(value = "select new com.example.demo.entity.TestName(t.testIndex,t.testName) from TestEntity t")
    List<TestName> find();

    /**
     * 返回最大的章节号
     */
    @Query(nativeQuery = true, value = "select MAX(test_index) from information.test ")
    int findMax();

}
