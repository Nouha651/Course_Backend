package com.example.SpringbootMicroservice1.controller;

import com.example.SpringbootMicroservice1.model.Answer;
import com.example.SpringbootMicroservice1.service.AnswerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PutMapping("/{answerId}")
    public ResponseEntity<?> updateAnswer(@PathVariable Long answerId, @RequestBody Answer updatedAnswer) {
        Answer answer = answerService.updateAnswer(answerId, updatedAnswer);

        if (answer != null) {
            return ResponseEntity.ok(answer);
        } else {
            return new ResponseEntity<>("Answer not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<?> getAnswerById(@PathVariable Long answerId) {
        Answer answer = answerService.findAnswerById(answerId);

        if (answer != null) {
            return ResponseEntity.ok(answer);
        } else {
            return new ResponseEntity<>("Answer not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Answer>> getAllAnswers() {
        List<Answer> answers = answerService.getAllAnswers();
        return ResponseEntity.ok(answers);
    }

    @PostMapping
    public ResponseEntity<Answer> saveAnswer(@RequestBody Answer answer) {
        Answer savedAnswer = answerService.saveAnswer(answer);
        return new ResponseEntity<>(savedAnswer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long answerId) {
        answerService.deleteAnswer(answerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<String> addAnswer(
            @RequestParam Long courseId,
            @RequestParam Long testId,
            @RequestParam Long questionId,
            @RequestBody List<Answer> answers) {

        try {
            answerService.addAnswers(courseId, testId, questionId, answers);
            return ResponseEntity.ok("Answers added successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course, Test, or Question not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding answers.");
        }
    }

}

