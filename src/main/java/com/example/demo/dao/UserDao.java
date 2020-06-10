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

    /**
     * 分页 未使用
     */
    @Query(nativeQuery = true, value = "select * from information.user")
    Page<UserEntity> find(Pageable pageable);

    /**
     * 测试用 未使用
     */
    @Query(nativeQuery = true, value = "select * from information.user where id = %?1")
    List<UserEntity> findTest(String name);

    /**
     * 登录用
     */
    @Query(nativeQuery = true, value = "select * from information.user where user.id = ?1 and user.password = ?2 and user.school = ?3 and user.class = ?4")
    UserEntity login(String id,String pass,String school,String clazz);

    /**
     * 返回一个班级所有的学生
     */
    @Query(nativeQuery = true, value = "select user.id from information.user where user.school = ?1 and user.class = ?2")
    List<String> findStudents(String school,String clazz);

    /**
     * 返回一个学校所有的学生
     */
    @Query(nativeQuery = true, value = "select user.id from information.user where user.school = ?1")
    List<String> findSchoolStudents(String school);

    /**
     * 返回一个班级所有的学生详细
     */
    @Query(nativeQuery = true, value = "select * from information.user where user.school = ?1 and user.class = ?2")
    List<UserEntity> findStudentsDetial(String school,String clazz);

    /**
     * 返回一个学校所有的学生详细
     */
    @Query(nativeQuery = true, value = "select * from information.user where user.school = ?1")
    List<UserEntity> findSchoolStudentsDetial(String school);



}
