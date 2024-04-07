package com.waqas.quizservice.feign;


import com.waqas.quizservice.ApiResponse;
import com.waqas.quizservice.data.QuestionsWrapper;
import com.waqas.quizservice.data.Responses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface  {

    @GetMapping("api/quiz/questions/generateQuestions")
    public List<Integer> generateQuestionsForQuiz(@RequestParam String type, @RequestParam int noOfQ);

    @PostMapping("api/quiz/questions/getQuestions")
    public  List<QuestionsWrapper> getQuestionsById(@RequestBody List<Integer> questionIds);

    @PostMapping("api/quiz/questions/getScore")
    public ResponseEntity<ApiResponse<?>> getScore(@RequestBody List<Responses> respones);
}
