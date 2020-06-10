package com.example.demo.dao;

import com.example.demo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * @author qq491
 */
public interface UserDao extends JpaRepository<UserEntity, String > {

    @Query(nativeQuery = true, value = "select * from information.user")
    Page<UserEntity> find(Pageable pageable);

    @Query(nativeQuery = true, value = "select * from information.user where id = %?1")
    List<UserEntity> findTest(String name);
}
