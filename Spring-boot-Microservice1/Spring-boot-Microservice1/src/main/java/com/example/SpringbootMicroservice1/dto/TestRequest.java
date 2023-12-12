package com.example.SpringbootMicroservice1.dto;

import java.util.List;
import lombok.Data;

@Data
public class TestRequest {


    private Long test_id;
    private String testName;
    private String testDescription;
    private Long course_id;

    /*private List<String> questionContents;
    private List<List<String>> suggestionContents;
    private List<List<String>> reponseContents;*/
    private List<TestQuestionRequest> questions;

    public List<TestQuestionRequest> getQuestions() {
        return questions;
    }


}

