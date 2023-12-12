package com.example.SpringbootMicroservice1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = true)
    @JsonIgnore
    private Question question;

    @Column(name = "text", length = 255, nullable = true)
    private String text;

    @Column(name = "note", nullable = true)
    private int note;

}

