package com.waqas.quizApp.controllers;

import com.waqas.quizApp.ApiResponse;
import com.waqas.quizApp.data.Responses;
import com.waqas.quizApp.repositories.QuestionsRepository;
import com.waqas.quizApp.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/getQuizbyTitleOrId")
    public ResponseEntity<ApiResponse<?>> getQuizbyTitleOrId(@RequestParam Integer id,@RequestParam String title){
        return ResponseEntity.ok(quizService.getQuiz(id,title));
    }

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<?>> submitQuiz(@RequestBody List<Responses> respones){
        return ResponseEntity.ok(quizService.submitQuiz(respones));
    }



}
