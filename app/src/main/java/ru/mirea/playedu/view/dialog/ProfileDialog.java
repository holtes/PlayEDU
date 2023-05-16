package ru.mirea.playedu.view.dialog;

import static ru.mirea.playedu.Constants.MALE_IC;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import ru.mirea.playedu.Constants;
import ru.mirea.playedu.R;
import ru.mirea.playedu.databinding.DialogAchievementBinding;
import ru.mirea.playedu.databinding.DialogProfileBinding;
import ru.mirea.playedu.model.Achievement;
import ru.mirea.playedu.model.Power;
import ru.mirea.playedu.view.adapter.AchievementAdapter;
import ru.mirea.playedu.view.adapter.PowerAdapter;
import ru.mirea.playedu.viewmodel.ProfileViewModel;

// Диалог с информацией о достижении
public class ProfileDialog extends DialogFragment {

    private View view;
    private ProfileViewModel profileViewModel;
    private RecyclerView achievementsList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = requireActivity().getLayoutInflater().inflate(
                R.layout.dialog_profile,
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
        DialogProfileBinding binding = DialogProfileBinding.inflate(getLayoutInflater());
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        binding.setViewModel(profileViewModel);
        if (Objects.equals(profileViewModel.getUser().getValue().getUserIcon(), MALE_IC)) {
            binding.profileImg.setImageResource(R.drawable.pic_magician);
        }
        else {
            binding.profileImg.setImageResource(R.drawable.ic_female_magician);
        }
        achievementsList = binding.powersList;


        builder.setView(binding.getRoot());
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        profileViewModel.getPickedAchievements().observe(getViewLifecycleOwner(), new Observer<ArrayList<Achievement>>() {
            @Override
            public void onChanged(ArrayList<Achievement> achievements) {
                bindRecyclerView(achievements);
            }
        });
    }

    private void bindRecyclerView(ArrayList<Achievement> achievements) {
        achievementsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        achievementsList.setNestedScrollingEnabled(false);
        achievementsList.setAdapter(new AchievementAdapter(achievements, new AchievementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Achievement achievement, int position) {
                PickAchievementDialog dialog = new PickAchievementDialog(position);
                dialog.show(getActivity().getSupportFragmentManager(), "Pick achievement dialog");
            }
        }, new AchievementAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Achievement achievement) {
                if (achievement.getAchievementId() != -1) {
                    AchievementDialog dialog = new AchievementDialog(achievement);
                    dialog.show(getActivity().getSupportFragmentManager(), "Achievement dialog");
                }
            }
        }));
    }
}

