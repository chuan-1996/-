package com.example.demo.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "classs", schema = "information", catalog = "")
public class ClasssEntity {
    private String className;

    public ClasssEntity() {
    }

    private String schoolName;
    private int classscol;

    public ClasssEntity(String className, String schoolName) {
        this.className = className;
        this.schoolName = schoolName;
        this.classscol=0;
    }

    @Basic
    @Column(name = "class_name")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Basic
    @Column(name = "school_name")
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Id
    @Column(name = "classscol")
    public int getClassscol() {
        return classscol;
    }

    public void setClassscol(int classscol) {
        this.classscol = classscol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClasssEntity that = (ClasssEntity) o;
        return classscol == that.classscol &&
                Objects.equals(className, that.className) &&
                Objects.equals(schoolName, that.schoolName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, schoolName, classscol);
    }
}
