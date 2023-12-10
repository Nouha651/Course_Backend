package com.example.SpringbootMicroservice1.controller;

import com.example.SpringbootMicroservice1.model.Suggestion;
import com.example.SpringbootMicroservice1.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {
    @Autowired
    private SuggestionService suggestionService;

    @PostMapping("/addSuggestion")
    public ResponseEntity<Suggestion> addSuggestion(@RequestBody Suggestion suggestion) {
        Suggestion savedSuggestion = suggestionService.addSuggestion(suggestion);
        return new ResponseEntity<>(savedSuggestion, HttpStatus.CREATED);
    }

}

