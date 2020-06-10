package com.example.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "test", schema = "information", catalog = "")
public class TestEntity {
    private int testIndex;
    private String testName;
    private List<QuestionEntity> questions=new ArrayList<QuestionEntity>();

    public void show(){
        System.out.println(this.toString());
        for(QuestionEntity i :questions){
            System.out.println(i.toString());
        }

    }
    @Override
    public String toString() {
        return "TestEntity{" +
                "testIndex=" + testIndex +
                ", testName='" + testName + '\'' +
                ", questions=";
    }


    @Id
    @Column(name = "test_index")
    public int getTestIndex() {
        return testIndex;
    }

    @OneToMany(mappedBy="index", cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setTestIndex(int testIndex) {
        this.testIndex = testIndex;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }

    @Basic
    @Column(name = "test_name")
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestEntity that = (TestEntity) o;
        return testIndex == that.testIndex &&
                Objects.equals(testName, that.testName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testIndex, testName);
    }
}
