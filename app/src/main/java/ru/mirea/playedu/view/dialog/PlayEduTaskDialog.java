package ru.mirea.playedu.view.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.mirea.playedu.Constants;
import ru.mirea.playedu.DimensionManager;
import ru.mirea.playedu.HorizontalMarginItemDecoration;
import ru.mirea.playedu.R;
import ru.mirea.playedu.callbacks.OnSelectColorFilterCallback;
import ru.mirea.playedu.databinding.DialogFightEndBinding;
import ru.mirea.playedu.databinding.DialogPlayEduTaskBinding;
import ru.mirea.playedu.model.Category;
import ru.mirea.playedu.model.PlayEduEvent;
import ru.mirea.playedu.model.PlayEduEventKillEnemy;
import ru.mirea.playedu.model.PlayEduEventNews;
import ru.mirea.playedu.model.PlayEduTask;
import ru.mirea.playedu.model.Quiz;
import ru.mirea.playedu.model.UserTask;
import ru.mirea.playedu.view.activity.MainActivity;
import ru.mirea.playedu.view.adapter.ColorAdapter;
import ru.mirea.playedu.viewmodel.AddTaskViewModel;
import ru.mirea.playedu.viewmodel.TasksViewModel;

// Диалог добавления задачи
public class PlayEduTaskDialog extends DialogFragment {

    private View view;
    private TasksViewModel viewModel;
    private PlayEduTask playEduTask;
    private DialogPlayEduTaskBinding binding;


    public PlayEduTaskDialog(PlayEduTask playEduTask) {
        this.playEduTask = playEduTask;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = requireActivity().getLayoutInflater().inflate(
                R.layout.dialog_play_edu_task,
                null,
                false);

        // Фон диалога
        getDialog().getWindow().setBackgroundDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_dialog));

        // Привязка viewmodel
        viewModel = new ViewModelProvider(requireActivity()).get(TasksViewModel.class);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog alert = builder.create();

        binding = DialogPlayEduTaskBinding.inflate(getLayoutInflater());
        binding.description.setText(playEduTask.getEvent().getDescription());
        binding.priceTxt.setText(Integer.toString(playEduTask.getCoinsReward()));
        if (playEduTask.getEvent().getClass() == PlayEduEventKillEnemy.class) {
            binding.transitionBtn.setVisibility(View.GONE);
        }

        builder.setView(binding.getRoot());
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (playEduTask.getEvent().getClass() == PlayEduEventNews.class) {
            binding.transitionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.completePlayEduTask(playEduTask);
                    PlayEduEventNews playEduEventNews = (PlayEduEventNews) playEduTask.getEvent();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(playEduEventNews.getUrl()));
                    startActivity(browserIntent);
                    dismiss();
                }
            });
        }
        else if (playEduTask.getEvent().getClass() == Quiz.class) {
            binding.transitionBtn.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("PlayEduTask", playEduTask);
                NavHostFragment.findNavController(this).navigate(R.id.action_questsFragment_to_quizFragment, bundle);
                dismiss();
            });
        }
    }
}