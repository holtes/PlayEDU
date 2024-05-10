package ru.mirea.playedu.usecases;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ru.mirea.playedu.model.Question;

public class GetQuestionsListUseCase {

    public ArrayList<Question> execute(String jsonString) throws JSONException {
        ArrayList<Question> questions = new ArrayList<>();
        JSONArray quiz = new JSONArray(jsonString);
        for (int i = 0; i < quiz.length(); i++) {
            JSONObject questionJson = quiz.getJSONObject(i);
            String questionText = questionJson.getString("questionText");
            JSONArray answers = questionJson.getJSONArray("answersTexts");
            ArrayList<String> answersTexts = new ArrayList<>();
            for (int j = 0; j < answers.length(); j++) {
                answersTexts.add(answers.getString(i));
            }
            int rightAnswerNum = questionJson.getInt("rightAnswerNum");
            Question question = new Question(questionText, answersTexts, rightAnswerNum);
            questions.add(question);
        }
        return questions;
    }
}
