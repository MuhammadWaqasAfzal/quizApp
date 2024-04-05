package com.waqas.quizApp.questions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions,Long> {

    @Query("select q from Questions q where q.questionType = ?1")
    Optional<List<Questions>> getTypeWiseQuestions(String type);
}
