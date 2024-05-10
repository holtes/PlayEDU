package ru.mirea.playedu.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import ru.mirea.playedu.data.repository.PlayEduTaskRepository;
import ru.mirea.playedu.data.repository.UserRepository;
import ru.mirea.playedu.data.storage.cache.PlayEduTaskCacheStorage;
import ru.mirea.playedu.data.storage.cache.UserCacheStorage;
import ru.mirea.playedu.model.PlayEduTask;
import ru.mirea.playedu.model.Question;
import ru.mirea.playedu.model.Response;
import ru.mirea.playedu.usecases.CompletePlayEduTaskUseCase;
import ru.mirea.playedu.usecases.GetQuestionsListUseCase;

public class QuizViewModel extends ViewModel {

    private CompletePlayEduTaskUseCase completePlayEduTaskUseCase;
    private GetQuestionsListUseCase getQuestionsListUseCase;
    private int rightAnswersCount = 0;

    private int questionsCount;
    private ArrayList<Question> questionsList;
    private final MutableLiveData<Integer> curQuestionInd = new MutableLiveData<>();

    public QuizViewModel() {
        UserRepository userRepository = new UserRepository(UserCacheStorage.getInstance());
        PlayEduTaskRepository playEduTaskRepository = new PlayEduTaskRepository(PlayEduTaskCacheStorage.getInstance());

        getQuestionsListUseCase = new GetQuestionsListUseCase();
        completePlayEduTaskUseCase = new CompletePlayEduTaskUseCase(playEduTaskRepository, userRepository);
    }

    public void setQuestionList(String jsonString) throws JSONException {
        questionsList = getQuestionsListUseCase.execute(jsonString);
        questionsCount = questionsList.size();
        curQuestionInd.setValue(0);
    }

    public LiveData<Integer> getCurQuestionInd() {
        return curQuestionInd;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }

    public boolean quizCompleted() {
        return rightAnswersCount == questionsCount;
    }
    public Question getQuestion(int index) {
        return questionsList.get(index);
    }

    public boolean answerQuestion(int num) {
        boolean status = false;
        if (questionsList.get(curQuestionInd.getValue()).getRightAnswerNum() == num){
            rightAnswersCount++;
            status = true;
        }
        curQuestionInd.setValue(curQuestionInd.getValue() + 1);
        return status;
    }

    public int completePlayEduTask(PlayEduTask playEduTask) {
        Response response = completePlayEduTaskUseCase.execute(playEduTask);
        return response.getCode();
    }

    public void refreshQuiz() {
        rightAnswersCount = 0;
        curQuestionInd.setValue(0);
    }




}
