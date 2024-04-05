package com.waqas.quizApp.controllers;

import com.waqas.quizApp.ApiResponse;
import com.waqas.quizApp.repositories.QuestionsRepository;
import com.waqas.quizApp.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/quiz")
public class QuizController {

    @Autowired
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createQuiz(@RequestParam String title,@RequestParam String type, @RequestParam int noOfQ){
        return ResponseEntity.ok(quizService.createQuiz(title,type,noOfQ));
    }

}
