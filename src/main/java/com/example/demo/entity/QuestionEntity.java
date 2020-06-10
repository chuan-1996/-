package com.example.demo.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "question", schema = "information", catalog = "")
public class QuestionEntity {
    private String a;
    private String b;
    private String c;
    private String d;
    private String title;
    private int index;
    private int questionId;
    private int answer;

    @Basic
    @Column(name = "A")
    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    @Basic
    @Column(name = "B")
    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    @Basic
    @Column(name = "C")
    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    @Basic
    @Column(name = "D")
    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "index")
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Id
    @Column(name = "question_id")
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Basic
    @Column(name = "answer")
    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionEntity that = (QuestionEntity) o;
        return index == that.index &&
                questionId == that.questionId &&
                answer == that.answer &&
                Objects.equals(a, that.a) &&
                Objects.equals(b, that.b) &&
                Objects.equals(c, that.c) &&
                Objects.equals(d, that.d) &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c, d, title, index, questionId, answer);
    }
}
