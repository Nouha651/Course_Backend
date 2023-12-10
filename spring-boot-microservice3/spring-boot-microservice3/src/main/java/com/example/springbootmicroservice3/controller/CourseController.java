package com.example.springbootmicroservice3.controller;

import com.example.springbootmicroservice3.request.CourseServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("gateway/course")//pre-path
public class CourseController
{
    @Autowired
    private CourseServiceRequest courseServiceRequest;

    @PostMapping //gateway/course
    public ResponseEntity<?> saveCourse(@RequestBody Object course)
    {
        return new ResponseEntity<>(courseServiceRequest.saveCourse(course), HttpStatus.CREATED);
    }

    @DeleteMapping("{courseId}")//gateway/course/{courseId}
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId)
    {
        courseServiceRequest.deleteCourse(courseId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping//gateway/course
    public ResponseEntity<?> getAllCourses()
    {
        return ResponseEntity.ok(courseServiceRequest.getAllCourses());
    }

    @GetMapping("/{courseId}") // gateway/course/{courseId}
    public ResponseEntity<?> getCourseById(@PathVariable Long courseId) {
        Object course = courseServiceRequest.getCourseById(courseId);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{courseId}") // gateway/course/{courseId}
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId, @RequestBody Object updatedCourse) {
        Object course = courseServiceRequest.updateCourse(courseId, updatedCourse);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
    }
}
