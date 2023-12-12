package com.example.SpringbootMicroservice1.repository;

import com.example.SpringbootMicroservice1.model.Question;
import com.example.SpringbootMicroservice1.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTestId(Long test_id);
}

