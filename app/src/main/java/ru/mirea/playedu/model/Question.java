package ru.mirea.playedu.model;

import java.util.ArrayList;

public class Question {
    private String questionText;
    private ArrayList<String> answersTexts;
    private int rightAnswerNum;

    public Question(String questionText, ArrayList<String> answersTexts, int rightAnswerNum) {
        this.questionText = questionText;
        this.answersTexts = answersTexts;
        this.rightAnswerNum = rightAnswerNum;
    }

    public String getQuestionText() {
        return questionText;
    }

    public ArrayList<String> getAnswersTexts() {
        return answersTexts;
    }

    public int getRightAnswerNum() {
        return rightAnswerNum;
    }
}
