package com.waqas.quizservice.data;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ElementCollection
    @JoinTable(
            name = "questions_list" // Custom table name
    )
    private List<Integer> questionsList;

    public Quiz(String title, List<Integer> questionsList) {
        this.title = title;
        this.questionsList = questionsList;
    }

    public Quiz() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Integer> questionsList) {
        this.questionsList = questionsList;
    }
}
