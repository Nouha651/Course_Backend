package com.example.SpringbootMicroservice1.repository;

import com.example.SpringbootMicroservice1.model.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    List<Suggestion> findByQuestionId(Long questionId);
}

