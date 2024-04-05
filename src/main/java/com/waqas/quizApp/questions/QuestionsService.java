package com.waqas.quizApp.questions;


import com.waqas.quizApp.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionsService {

    @Autowired
    private final QuestionsRepository questionsRepository;

    public QuestionsService(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    public ApiResponse<List<Questions>> getAllQuestions(){
        return  ApiResponse.ok(questionsRepository.findAll());
    }

    public ApiResponse<?> addQuestion(Questions question) {
        return ApiResponse.ok(questionsRepository.save(question));
    }

    public ApiResponse<Optional<List<Questions>>> getTypeWiseQuestions(String type) {
        return  ApiResponse.ok(questionsRepository.getTypeWiseQuestions(type));
    }

    public ApiResponse<?> updateQuestion(Questions question) {
        if (!questionsRepository.existsById(question.getQuestionId())) {
            return  ApiResponse.error(404,"Question not found");
        }

        return ApiResponse.ok(questionsRepository.save(question));
    }

    public ApiResponse<?> deleteQuestion(Integer id) {
        if (!questionsRepository.existsById(Long.valueOf(id))) {
            return  ApiResponse.error(404,"Question not found");
        }

        questionsRepository.deleteById(Long.valueOf(id));

        return ApiResponse.ok("");
    }
}
