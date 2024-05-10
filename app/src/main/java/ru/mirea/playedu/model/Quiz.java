package ru.mirea.playedu.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class Quiz extends PlayEduEvent {

    private JSONArray quizData;

    public Quiz(int eventId, String description, JSONArray quizData) {
        super(eventId, description);
        this.quizData = quizData;
    }

    public JSONArray getQuizData() {
        return quizData;
    }

    public void setQuizData(JSONArray quizData) {
        this.quizData = quizData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quiz quiz = (Quiz) o;

        return quizData.equals(quiz.quizData);
    }

    @Override
    public int hashCode() {
        return quizData.hashCode();
    }
}
