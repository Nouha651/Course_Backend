package com.example.SpringbootMicroservice1.service;

import com.example.SpringbootMicroservice1.model.Question;
import com.example.SpringbootMicroservice1.model.Suggestion;
import com.example.SpringbootMicroservice1.repository.QuestionRepository;
import com.example.SpringbootMicroservice1.repository.SuggestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SuggestionService {
    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Transactional
    public Suggestion addSuggestion(Suggestion suggestion) {
        // Assurez-vous que la question est non null
        if (suggestion.getQuestion() == null) {
            throw new IllegalArgumentException("La suggestion doit être associée à une question.");
        }

        // Assurez-vous que l'ID de la question n'est pas null
        Long questionId = suggestion.getQuestion().getId();
        if (questionId == null) {
            throw new IllegalArgumentException("L'ID de la question ne doit pas être null.");
        }

        // Assurez-vous que la question existe en base de données
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question with id " + questionId + " not found"));

        // Associez la suggestion à la question
        suggestion.setQuestion(question);

        // Enregistrez la suggestion en base de données
        return suggestionRepository.save(suggestion);
    }


}

