package com.example.SpringbootMicroservice1.controller;

import com.example.SpringbootMicroservice1.dto.TestQuestionRequest;
import com.example.SpringbootMicroservice1.dto.TestRequest;
import com.example.SpringbootMicroservice1.model.Test;
import com.example.SpringbootMicroservice1.repository.QuestionRepository;
import com.example.SpringbootMicroservice1.repository.TestRepository;
import com.example.SpringbootMicroservice1.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private TestService testService;

    @PutMapping("/{testId}")
    public ResponseEntity<?> updateTest(@PathVariable Long testId, @RequestBody Test updatedTest) {
        Test test = testService.updateTest(testId, updatedTest);

        if (test != null) {
            return ResponseEntity.ok(test);
        } else {
            return new ResponseEntity<>("Test not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{testId}")
    public ResponseEntity<?> getTestById(@PathVariable Long testId) {
        Test test = testService.findTestById(testId);

        if (test != null) {
            return ResponseEntity.ok(test);
        } else {
            return new ResponseEntity<>("Test not found", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping
    public List<Test> getAllTests() {
        return testService.getAllTests();
    }


    @PostMapping
    public ResponseEntity<Test> saveTest(@RequestBody Test test) {
        Test savedTest = testService.saveTest(test);
        return new ResponseEntity<>(savedTest, HttpStatus.CREATED);
    }

    @DeleteMapping("/{testId}")
    public ResponseEntity<?> deleteTest(@PathVariable Long testId) {
        testService.deleteTest(testId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @PostMapping("/addTest")
    public ResponseEntity<?> addTestWithQuestionsAndAnswers(@RequestBody TestRequest request) {
        String testName = request.getTestName();
        String testDescription = request.getTestDescription();
        Long course_id = request.getCourse_id(); // Assurez-vous que le nom du paramètre correspond

        List<TestQuestionRequest> questionRequests = request.getQuestions();

        Test test = testService.addTestWithQuestionsAndAnswers(testName, testDescription, course_id, questionRequests);

        return ResponseEntity.ok(test);
    }

    @GetMapping("/byCourse/{courseId}")
    public ResponseEntity<List<Test>> getTestsByCourse(@PathVariable Long courseId) {
        List<Test> tests = testService.getTestsByCourseId(courseId);
        return new ResponseEntity<>(tests, HttpStatus.OK);
    }
    @GetMapping("/rep/byCourse/{courseId}")
    public ResponseEntity<List<Test>> getTestsByCourseId(@PathVariable Long courseId) {
        List<Test> tests = testService.getTestsrepByCourseId(courseId);
        return ResponseEntity.ok(tests);
    }
    @Autowired
    private TestRepository testRepository;
    @PostMapping("/calculateTotalNote/{testId}") //tests/calculateTotalNote/{testId}
    public ResponseEntity<String> calculateTotalNote(@PathVariable Long testId) {
        Optional<Test> optionalTest = testRepository.findById(testId);

        if (optionalTest.isPresent()) {
            Test test = optionalTest.get();
            testService.calculateTotalNote(test);
            testRepository.save(test); // Save the updated test
            return ResponseEntity.ok("Total note calculated and updated for Test with ID: " + testId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test not found with ID: " + testId);
        }
    }

}
