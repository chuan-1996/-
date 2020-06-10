package com.example.demo.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "score", schema = "information", catalog = "")
public class ScoreEntity {
    private int testIndex;
    private String userId;
    private int score;
    private int scoreId;

    public ScoreEntity(int testIndex, String userId, int score) {
        this.testIndex = testIndex;
        this.userId = userId;
        this.score = score;
    }

    public ScoreEntity() {
    }

    public ScoreEntity(int testIndex, String userId, int score, int scoreId) {
        this.testIndex = testIndex;
        this.userId = userId;
        this.score = score;
        this.scoreId = scoreId;
    }

    @Basic
    @Column(name = "test_index")
    public int getTestIndex() {
        return testIndex;
    }

    public void setTestIndex(int testIndex) {
        this.testIndex = testIndex;
    }

    @Basic
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "score")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Id
    @Column(name = "score_id")
    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreEntity that = (ScoreEntity) o;
        return testIndex == that.testIndex &&
                score == that.score &&
                scoreId == that.scoreId &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testIndex, userId, score, scoreId);
    }
}
