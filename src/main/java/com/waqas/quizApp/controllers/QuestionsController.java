package com.waqas.quizApp.controllers;


import com.waqas.quizApp.ApiResponse;
import com.waqas.quizApp.data.Questions;
import com.waqas.quizApp.services.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/questions")
public class QuestionsController {

    @Autowired
    private final QuestionsService questionsService;

    public QuestionsController(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    @GetMapping("/getAllQuestions")
    public ResponseEntity<ApiResponse<?>> getAllQuestions(){
        ApiResponse<List<Questions>> apiResponse =  questionsService.getAllQuestions();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<ApiResponse<?>> addQuestion(@RequestBody Questions question){
        ApiResponse<?> apiResponse = questionsService.addQuestion(question);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/updateQuestion")
    public ResponseEntity<ApiResponse<?>> updateQuestion(@RequestBody Questions question){
        ApiResponse<?> apiResponse = questionsService.updateQuestion(question);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/deleteQuestion")
    public ResponseEntity<ApiResponse<?>> deleteQuestion(Integer id){
        ApiResponse<?> apiResponse = questionsService.deleteQuestion(id);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getTypeWiseQuestions")
    public ResponseEntity<ApiResponse<?>> getTypeWiseQuestions(String type){
        ApiResponse<Optional<List<Questions>>> apiResponse =  questionsService.getTypeWiseQuestions(type);
        return ResponseEntity.ok(apiResponse);
    }

}
