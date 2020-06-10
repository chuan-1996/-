package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TestName implements Serializable {

    @JsonProperty("test_index")
    private int testIndex;

    @JsonProperty("test_name")
    private String testName;

    public int getTestIndex() {
        return testIndex;
    }

    public TestName() {
    }

    public String getTestName() {
        return testName;
    }

    public void setTestIndex(int testIndex) {
        this.testIndex = testIndex;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public TestName(int testIndex, String testName) {
        this.testIndex = testIndex;
        this.testName = testName;
    }
}
