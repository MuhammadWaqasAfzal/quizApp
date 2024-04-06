package com.waqas.quizApp.repositories;

import com.waqas.quizApp.data.Questions;
import com.waqas.quizApp.data.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz,Long> {

    @Query("SELECT q FROM Quiz q WHERE q.title = :title")
    Optional<Quiz> findByTitle(String title);

//    @Query("Select q from questions_list q where q.quiz_id = :id")
//    List<Questions> getQuizQuestions(Integer id);
}
