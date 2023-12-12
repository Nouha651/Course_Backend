package com.example.SpringbootMicroservice1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.*;

@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString
@Entity
@Table(name = "test")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", unique = false)
    @JsonIgnore
    private Course course;

    @Column(name = "name", length = 100, nullable = true)
    private String name;

    @Column(name = "description", length = 255, nullable = true)
    private String description;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Question> questions = new LinkedList<>();

    @Column(name = "noteTotal", nullable = true)
    private int noteTotal;

    public void addQuestion(Question question) {
        questions.add(question);
        question.setTest(this);
    }

    public void addSuggestionToQuestion(Long questionId, Suggestion suggestion) {
        Question question = findQuestionById(questionId);
        if (question != null) {
            question.addSuggestion(suggestion);
        }
    }

    private Question findQuestionById(Long questionId) {
        return questions.stream()
                .filter(question -> question.getId().equals(questionId))
                .findFirst()
                .orElse(null);
    }

    public void addCourse(Course course) {
        this.course = course;
        course.getTests().add(this);
    }
}
