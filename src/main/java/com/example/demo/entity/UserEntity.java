package com.example.demo.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "information", catalog = "")
public class UserEntity {
    private String user;
    private String id;
    private int permission;
    private String password;
    private String school;
    private String clazz;

    @Basic
    @Column(name = "user")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "permission")
    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "school")
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Basic
    @Column(name = "class")
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public UserEntity(String id, String password, String school, String clazz) {
        this.id = id;
        this.password = password;
        this.school = school;
        this.clazz = clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return permission == that.permission &&
                Objects.equals(user, that.user) &&
                Objects.equals(id, that.id) &&
                Objects.equals(password, that.password) &&
                Objects.equals(school, that.school) &&
                Objects.equals(clazz, that.clazz);
    }

    public UserEntity(String user, String id, int permission, String password, String school, String clazz) {
        this.user = user;
        this.id = id;
        this.permission = permission;
        this.password = password;
        this.school = school;
        this.clazz = clazz;
    }

    public UserEntity() {
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "user='" + user + '\'' +
                ", id='" + id + '\'' +
                ", permission=" + permission +
                ", password='" + password + '\'' +
                ", school='" + school + '\'' +
                ", clazz='" + clazz + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, id, permission, password, school, clazz);
    }
}
