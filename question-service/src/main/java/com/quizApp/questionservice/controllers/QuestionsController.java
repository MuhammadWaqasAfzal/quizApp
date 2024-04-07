package com.quizApp.questionservice.controllers;



import com.quizApp.questionservice.ApiResponse;
import com.quizApp.questionservice.data.Questions;
import com.quizApp.questionservice.data.QuestionsWrapper;
import com.quizApp.questionservice.data.Responses;
import com.quizApp.questionservice.services.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/quiz/questions")
public class QuestionsController {

    @Autowired
    private final QuestionsService questionsService;

    @Autowired
    private Environment environment;

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

    @GetMapping("/generateQuestions")
    public ResponseEntity<List<Long>> generateQuestionsForQuiz(@RequestParam String type, @RequestParam int noOfQ){
        return ResponseEntity.ok(questionsService.generateQuestions(type,noOfQ));
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionsWrapper>> getQuestionsById(@RequestBody List<Integer> questionIds){
        System.out.println(environment.getProperty("local.server.port"));
        return ResponseEntity.ok(questionsService.getQuestionById(questionIds));
    }

    @PostMapping("/getScore")
    public ResponseEntity<ApiResponse<?>> getScore(@RequestBody List<Responses> respones){
        return ResponseEntity.ok(questionsService.calculateScore(respones));
    }
}
