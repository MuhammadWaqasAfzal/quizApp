package com.quizApp.questionservice.services;


import com.quizApp.questionservice.ApiResponse;
import com.quizApp.questionservice.data.Questions;
import com.quizApp.questionservice.data.QuestionsWrapper;
import com.quizApp.questionservice.data.Responses;
import com.quizApp.questionservice.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Long> generateQuestions(String type, int noOfQ) {
        return getRandomQuestionsOfType(questionsRepository.getTypeWiseQuestions(type),noOfQ);
    }

    private List<Long> getRandomQuestionsOfType(Optional<List<Questions>> questions, int count) {
        List<Long> result = new ArrayList<>();
        if (questions.isPresent()) {
            List<Questions> questionList = questions.get();

                // Shuffle the list
                Collections.shuffle(questionList);

                // Select the first 'count' elements
                for (int i = 0; i < count && i< questionList.size(); i++) {
                    result.add( questionList.get(i).getQuestionId());
                }
        }
        return result;
    }

    public List<QuestionsWrapper> getQuestionById(List<Integer> questionIds) {
        List<Long> longs = questionIds.stream() // Create a stream
                .mapToLong(Integer::longValue) // Map from Integer to long
                .boxed()                       // Box the long values to Long objects
                .collect(Collectors.toList()); // Collect the results into a list

        List<Questions> questions = questionsRepository.findAllById(longs);
        List<QuestionsWrapper> questionsWrappers = new ArrayList<>();
        for (Questions q: questions){
            questionsWrappers.add(new QuestionsWrapper(q.getQuestionId(),q.getQuestionType(),q.getQuestion(),
                    q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4()));
        }

        return questionsWrappers;
    }

    public ApiResponse<?> calculateScore(List<Responses> responses) {
        int total = responses.size(),right=0,wrong = 0;
        for(Responses response : responses){
            Optional<Questions> question = questionsRepository.findById(Long.valueOf(response.getId()));
            if(question.isPresent()){
                if(question.get().getRightAnswer().equalsIgnoreCase(response.getAnswer().toLowerCase())){
                    right++;
                }
            }
        }

        Map<String, Integer> data = new HashMap<>();

        data.put("total",total);
        data.put("right",right);
        data.put("wrong",total-right);
        return ApiResponse.ok(data);
    }

}
