package com.example.SpringbootMicroservice1.repository;

import com.example.SpringbootMicroservice1.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long>
{
    //findBy+fieldName
}
