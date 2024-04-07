package com.waqas.quizservice.services;



import com.waqas.quizservice.ApiResponse;
import com.waqas.quizservice.data.QuestionsWrapper;
import com.waqas.quizservice.data.Quiz;
import com.waqas.quizservice.data.Responses;
import com.waqas.quizservice.feign.QuizInterface;
import com.waqas.quizservice.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {



    @Autowired
    private final QuizRepository quizRepository;

    @Autowired
    private final QuizInterface quizInterface;

    public QuizService(QuizRepository quizRepository, QuizInterface quizInterface) {
        this.quizRepository = quizRepository;
        this.quizInterface = quizInterface;
    }

    public ApiResponse<?> createQuiz(String title,String type, int numOfQ){

        if(quizRepository.findByTitle(title).isPresent()){
            return ApiResponse.error(404,"Quiz with this title already exists");
        }

        System.out.println(quizInterface.generateQuestionsForQuiz(type, numOfQ));
        List<Integer> questions = quizInterface.generateQuestionsForQuiz(type, numOfQ);

        quizRepository.save(new Quiz(title,questions));




        return ApiResponse.ok("");

    }


    public ApiResponse<?> getQuiz(Integer id, String title) {
        Optional<Quiz> quiz ;
        if(id!=null){
           quiz = quizRepository.findById(Long.valueOf(id));
        }else{
            quiz = quizRepository.findByTitle(title);
        }

        if(quiz.isPresent()){

            List<QuestionsWrapper> questions = (List<QuestionsWrapper>) quizInterface.getQuestionsById(quiz.get().getQuestionsList());
            return ApiResponse.ok(questions);
        }
        else{
            return ApiResponse.error(404,"No quiz found");
        }
    }

//    public ApiResponse<?> submitQuiz(List<Responses> responses) {
////        int total = quizInterface.getScore(responses).getBody().getData().get("result");
////
////
////
////        Map<String, Integer> data = new HashMap<>();
////
////        data.put("total",total);
////        data.put("right",right);
////        data.put("wrong",wrong);
////        return ApiResponse.ok(data);
//    }
}
