package com.example.springbootmicroservice3.controller;

import com.example.springbootmicroservice3.request.CourseServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("gateway/test")
public class TestController {

    @Autowired
    private CourseServiceRequest testServiceRequest;



    @PostMapping("test") // Ajoutez un chemin approprié
    public ResponseEntity<?> saveTest(@RequestBody Object test) {
        return new ResponseEntity<>(testServiceRequest.saveTest(test), HttpStatus.CREATED);
    }

    @DeleteMapping("{testId}") // Ajoutez un chemin approprié
    public ResponseEntity<?> deleteTest(@PathVariable Long testId) {
        testServiceRequest.deleteTest(testId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping // Ajoutez un chemin approprié
    public ResponseEntity<?> getAllTests() {
        return ResponseEntity.ok(testServiceRequest.getAllTests());
    }

    @GetMapping("{testId}") // Ajoutez un chemin approprié
    public ResponseEntity<?> getTestById(@PathVariable Long testId) {
        Object test = testServiceRequest.getTestById(testId);
        if (test != null) {
            return ResponseEntity.ok(test);
        } else {
            return new ResponseEntity<>("Test not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{testId}") // Ajoutez un chemin approprié
    public ResponseEntity<?> updateTest(@PathVariable Long testId, @RequestBody Object updatedTest) {
        Object test = testServiceRequest.updateTest(testId, updatedTest);
        if (test != null) {
            return ResponseEntity.ok(test);
        } else {
            return new ResponseEntity<>("Test not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("addTest") // Ajoutez un chemin approprié
    public ResponseEntity<?> addTestWithQuestionsAndAnswers(@RequestBody Object request) {
        return ResponseEntity.ok(testServiceRequest.addTestWithQuestionsAndAnswers(request));
    }

    @GetMapping("byCourse/{courseId}") // Ajoutez un chemin approprié
    public ResponseEntity<?> getTestsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(testServiceRequest.getTestsByCourse(courseId));
    }

    @GetMapping("rep/byCourse/{courseId}") // Ajoutez un chemin approprié
    public ResponseEntity<?> getTestsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(testServiceRequest.getTestsByCourseId(courseId));
    }
}

