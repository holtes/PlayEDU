package ru.mirea.playedu.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import ru.mirea.playedu.R;
import ru.mirea.playedu.databinding.FragmentQuizBinding;
import ru.mirea.playedu.model.PlayEduEvent;
import ru.mirea.playedu.model.PlayEduTask;
import ru.mirea.playedu.model.Question;
import ru.mirea.playedu.model.Quiz;
import ru.mirea.playedu.view.adapter.AnswersAdapter;
import ru.mirea.playedu.viewmodel.QuizViewModel;
import ru.mirea.playedu.viewmodel.TasksViewModel;

public class QuizFragment extends Fragment {

    private FragmentQuizBinding binding;

    private QuizViewModel quizViewModel;

    private PlayEduTask playEduTask;

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        assert args != null;
        playEduTask = (PlayEduTask) args.getSerializable("PlayEduTask");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentQuizBinding.inflate(getLayoutInflater());
        quizViewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
        binding.setViewModel(quizViewModel);

        Quiz quiz = (Quiz) playEduTask.getEvent();
        binding.startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.startScreen.setVisibility(View.GONE);
                binding.content.setVisibility(View.VISIBLE);
                try {
                    Log.d("myLogs", quiz.getQuizData().toString());
                    quizViewModel.setQuestionList(quiz.getQuizData().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                quizViewModel.getCurQuestionInd().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer >= quizViewModel.getQuestionsCount()) {
                            binding.content.setVisibility(View.GONE);
                            binding.endScreen.setVisibility(View.VISIBLE);
                            setQuizResults(quizViewModel.quizCompleted());
                            return;
                        }
                        Question question = quizViewModel.getQuestion(integer);
                        binding.questionTxt.setText(question.getQuestionText());
                        AnswersAdapter adapter = new AnswersAdapter(question.getAnswersTexts(), new AnswersAdapter.OnSelectAnswerCallback() {
                            @Override
                            public void OnItemClick(int num) {
                                quizViewModel.answerQuestion(num);
                            }
                        });
                        binding.answers.setAdapter(adapter);
                    }
                });
            }
        });
        return binding.getRoot();
    }

    private void setQuizResults(boolean result) {
        if (result) {
            binding.status.setText(R.string.greetings);
            binding.reward.setVisibility(View.VISIBLE);
            binding.rewardText.setText(String.valueOf(playEduTask.getCoinsReward()));
            binding.endQuiz.setText(R.string.back_to_quests);
            quizViewModel.completePlayEduTask(playEduTask);
        }
        else {
            binding.reward.setVisibility(View.INVISIBLE);
            binding.status.setText(R.string.failure);
            binding.endQuiz.setText(R.string.retry);
        }
        binding.endQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result) Navigation.findNavController(v).navigate(R.id.action_quizFragment_to_questsFragment);
                else {
                    binding.content.setVisibility(View.VISIBLE);
                    binding.endScreen.setVisibility(View.GONE);
                    quizViewModel.refreshQuiz();
                }
            }
        });
    }
}