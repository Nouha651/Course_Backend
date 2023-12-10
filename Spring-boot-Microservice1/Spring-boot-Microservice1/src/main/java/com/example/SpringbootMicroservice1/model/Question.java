package com.example.SpringbootMicroservice1.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
@Data
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // @JdbcTypeCode(SqlTypes.JSON)
    @ManyToOne()
    @JoinColumn(name = "test_id", nullable = true)
    //@JsonBackReference
    @JsonIgnore
    private Test test;


    @Column(name = "text", length = 255, nullable = true)
    private String text;

    @Column(name = "type", length = 20, nullable = true)
    private String type;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private List<Answer> answers;



    /*@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)

    private List<Suggestion> suggestions;*/
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    @Column(name = "suggestions", nullable = true)
    private List<Suggestion> suggestions = new ArrayList<>();



    public void addSuggestion(Suggestion suggestion) {
        if (suggestions == null) {
            suggestions = new ArrayList<>();  // Assurez-vous que la liste est initialisée
        }
        suggestions.add(suggestion);
        suggestion.setQuestion(this);
    }


    public void addReponse(Answer reponse) {
        answers.add(reponse);
        reponse.setQuestion(this);
    }

    private Suggestion findSuggestionById(Long suggestionId) {
        return suggestions.stream()
                .filter(suggestion -> suggestion.getId().equals(suggestionId))
                .findFirst()
                .orElse(null);
    }
}

