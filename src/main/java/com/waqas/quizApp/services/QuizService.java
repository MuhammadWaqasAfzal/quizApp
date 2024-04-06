package com.waqas.quizApp.services;


import com.waqas.quizApp.ApiResponse;
import com.waqas.quizApp.data.Questions;
import com.waqas.quizApp.data.QuestionsWrapper;
import com.waqas.quizApp.data.Quiz;
import com.waqas.quizApp.data.Responses;
import com.waqas.quizApp.repositories.QuestionsRepository;
import com.waqas.quizApp.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    private final QuestionsRepository questionsRepository;

    @Autowired
    private final QuizRepository quizRepository;

    public QuizService(QuestionsRepository questionsRepository, QuizRepository quizRepository) {
        this.questionsRepository = questionsRepository;
        this.quizRepository = quizRepository;
    }

    public ApiResponse<?> createQuiz(String title,String type, int numOfQ){

        if(quizRepository.findByTitle(title).isPresent()){
            return ApiResponse.error(404,"Quiz with this title already exists");
        }
        Optional<List<Questions>> questions = questionsRepository.getTypeWiseQuestions(type);

        List<Questions> randomQuestions = getRandomQuestionsOfType(questions,numOfQ);


        quizRepository.save(new Quiz(title,randomQuestions));
        return ApiResponse.ok(randomQuestions);

    }


    private List<Questions> getRandomQuestionsOfType(Optional<List<Questions>> questions, int count) {
        List<Questions> result = new ArrayList<>();

        if (questions.isPresent()) {
            List<Questions> questionList = questions.get();
            int size = questionList.size();

            if (size <= count) {
                return questionList;
            } else {
                // Shuffle the list
                Collections.shuffle(questionList);

                // Select the first 'count' elements
                for (int i = 0; i < count; i++) {
                    result.add(questionList.get(i));
                }
            }
        }

        return result;
    }

    public ApiResponse<?> getQuiz(Integer id, String title) {
        Optional<Quiz> quiz ;
        if(id!=null){
           quiz = quizRepository.findById(Long.valueOf(id));
        }else{
            quiz = quizRepository.findByTitle(title);
        }

        if(quiz.isPresent()){
            List<QuestionsWrapper> questionsWrappers = new ArrayList<>();
            for(Questions q:quiz.get().getQuestionsList()){
                questionsWrappers.add(new QuestionsWrapper(q.getQuestionId(),q.getQuestionType(),q.getQuestion(),
                        q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4()));
            }
            return ApiResponse.ok(questionsWrappers);
        }
        else{
            return ApiResponse.error(404,"No quiz found");
        }
    }

    public ApiResponse<?> submitQuiz(List<Responses> responses) {
        int total = responses.size(),right=0,wrong = 0;
        for(Responses response : responses){
            Optional<Questions> question = questionsRepository.findById(Long.valueOf(response.getId()));
            if(question.isPresent()){
                if(question.get().getRightAnswer().equalsIgnoreCase(response.getAnswer().toLowerCase())){
                    right++;
                }else{
                    wrong++;
                }
            }
        }

        Map<String, Integer> data = new HashMap<>();

        data.put("total",total);
        data.put("right",right);
        data.put("wrong",wrong);
        return ApiResponse.ok(data);
    }
}
