package ru.mirea.playedu.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.mirea.playedu.R;
import ru.mirea.playedu.databinding.DialogAchievementBinding;
import ru.mirea.playedu.model.Achievement;

// Диалог с информацией о достижении
public class AchievementDialog extends DialogFragment {

    private View view;
    private Achievement achievement;
    private boolean isAchievementUnlocked;

    public AchievementDialog(Achievement achievement) {
        this.achievement = achievement;
    }

    public AchievementDialog(Achievement achievement, boolean isAchievementUnlocked) {
        this.achievement = achievement;
        this.isAchievementUnlocked = isAchievementUnlocked;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = requireActivity().getLayoutInflater().inflate(
                R.layout.dialog_achievement,
                null,
                false);

        // Фон диалога
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
        return view;
    }

    // Создание диалога
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Получение binding диалога
        DialogAchievementBinding binding = DialogAchievementBinding.inflate(getLayoutInflater());
        if (isAchievementUnlocked) {
            String title = getString(R.string.new_achievement);
            Achievement newAchievement = new Achievement(-1, 0, title, achievement.getDescription(), false, achievement.getIcon());
            binding.setAchievement(newAchievement);
            binding.achvmntImg.setImageResource(newAchievement.getIcon());
        }
        else {
            binding.setAchievement(achievement);
            binding.achvmntImg.setImageResource(achievement.getIcon());
        }



        builder.setView(binding.getRoot());
        return builder.create();
    }

}

