package com.example.SpringbootMicroservice1.dto;

import com.example.SpringbootMicroservice1.model.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class TestQuestionRequest {
    private String questionContent;
    private QuestionType questionType;
    private List<String> suggestionContents;
    /*private List<String> reponseContents;*/

}

