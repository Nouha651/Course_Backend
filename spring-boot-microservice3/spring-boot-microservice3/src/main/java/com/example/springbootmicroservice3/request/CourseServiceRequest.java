package com.example.springbootmicroservice3.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "course-service"//Name of course-service application
        , path = ""//Pre-path for course-service
        //,url = "${course.service.url}"
        ,configuration = FeignConfiguration.class
)
public interface CourseServiceRequest
{
    @PostMapping("/api/course/save")
///api/course/save
    Object saveCourse(@RequestBody Object requestBody);

    @DeleteMapping("/api/course/{courseId}")//api/course/{courseId}
    void deleteCourse(@PathVariable("courseId") Long courseId);

    @GetMapping("/api/course")
//api/course
    List<Object> getAllCourses();

    @GetMapping("/api/course/{courseId}")
    Object getCourseById(@PathVariable("courseId") Long courseId);

    @PutMapping("/api/course/{courseId}")
    Object updateCourse(@PathVariable("courseId") Long courseId, @RequestBody Object updatedCourse);

    @PutMapping("/tests/{testId}")
    Object updateTest(@PathVariable Long testId, @RequestBody Object updatedTest);

    @GetMapping("/tests/{testId}")
    Object getTestById(@PathVariable Long testId);

    @GetMapping("/tests")
    List<Object> getAllTests();

    @PostMapping("/tests")
    Object saveTest(@RequestBody Object test);

    @DeleteMapping("/tests/{testId}")
    void deleteTest(@PathVariable Long testId);

    @PostMapping("/tests/addTest")
    Object addTestWithQuestionsAndAnswers(@RequestBody Object request);

    @GetMapping("/tests/byCourse/{courseId}")
    List<Object> getTestsByCourse(@PathVariable Long courseId);

    @GetMapping("/tests/rep/byCourse/{courseId}")
    List<Object> getTestsByCourseId(@PathVariable Long courseId);
}
