package com.example.SpringbootMicroservice1.service;

import com.example.SpringbootMicroservice1.model.Answer;
import com.example.SpringbootMicroservice1.model.Question;
import com.example.SpringbootMicroservice1.repository.AnswerRepository;
import com.example.SpringbootMicroservice1.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Answer updateAnswer(Long answerId, Answer updatedAnswer) {
        Optional<Answer> existingAnswer = answerRepository.findById(answerId);

        if (existingAnswer.isPresent()) {
            Answer answer = existingAnswer.get();
            answer.setText(updatedAnswer.getText());
            // Autres propriétés à mettre à jour si nécessaire

            return answerRepository.save(answer);
        }

        return null;
    }

    public Answer findAnswerById(Long answerId) {
        Optional<Answer> answer = answerRepository.findById(answerId);
        return answer.orElse(null);
    }

    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public void deleteAnswer(Long answerId) {
        answerRepository.deleteById(answerId);
    }
    @Transactional
    public void addAnswers(Long courseId, Long testId, Long questionId, List<Answer> answers) {
        // Assurez-vous que la question existe
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));

        // Associez chaque réponse à la question
        answers.forEach(answer -> {
            answer.setQuestion(question);
            answerRepository.save(answer);
        });
    }
}

