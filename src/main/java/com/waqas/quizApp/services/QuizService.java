package com.waqas.quizApp.services;


import com.waqas.quizApp.ApiResponse;
import com.waqas.quizApp.data.Questions;
import com.waqas.quizApp.data.Quiz;
import com.waqas.quizApp.repositories.QuestionsRepository;
import com.waqas.quizApp.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
}
