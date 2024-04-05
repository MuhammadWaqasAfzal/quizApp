package com.waqas.quizApp.repositories;

import com.waqas.quizApp.data.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
}
