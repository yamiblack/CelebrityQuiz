package com.example.celebrityquiz;

import java.io.Serializable;

// Bridge class connecting MainActivity with QuizAdapter
// Serialize to extract objects from intent in SolutionActivity
public class Quiz implements Serializable {
    String question;
    String imageUrl;
    String one;
    String two;
    String three;
    String four;
    int correctAnswer;
    int userAnswer;

    Quiz(String question, String imageUrl, String one, String two,
         String three, String four, int correctAnswer, int userAnswer) {
        this.question = question;
        this.imageUrl = imageUrl;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
    }
}