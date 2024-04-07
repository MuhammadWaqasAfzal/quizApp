package com.waqas.quizservice.repositories;


import com.waqas.quizservice.data.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz,Long> {

    @Query("SELECT q FROM Quiz q WHERE q.title = :title")
    Optional<Quiz> findByTitle(String title);

//    @Query("Select q from questions_list q where q.quiz_id = :id")
//    List<Questions> getQuizQuestions(Integer id);
}
