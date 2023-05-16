package ru.mirea.playedu.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.mirea.playedu.R;
import ru.mirea.playedu.model.Achievement;
import ru.mirea.playedu.model.Power;
import ru.mirea.playedu.view.adapter.AchievementAdapter;
import ru.mirea.playedu.view.adapter.PowerAdapter;
import ru.mirea.playedu.viewmodel.GameViewModel;
import ru.mirea.playedu.viewmodel.ProfileViewModel;

public class PickAchievementDialog extends DialogFragment {

    private View view;
    private RecyclerView pickableAchievements;
    private ProfileViewModel profileViewModel;
    private int selectedPosition;

    public PickAchievementDialog(int position) {
        selectedPosition = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = requireActivity().getLayoutInflater().inflate(
                R.layout.dialog_pick_power,
                null,
                false);

        // Фон диалога
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_dialog);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        view = requireActivity().getLayoutInflater().
                inflate(R.layout.dialog_pick_power, null, false);

        pickableAchievements = view.findViewById(R.id.pick_power_list);

        // Инициализация ViewModel
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        bindRecyclerView(profileViewModel.getUnclockedAchievementsList());
        builder.setView(view);
        return builder.create();
    }

    private void bindRecyclerView(ArrayList<Achievement> achievements) {
        // Табличный RecyclerView для списка купленных сил
        pickableAchievements.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        pickableAchievements.setNestedScrollingEnabled(false);
        pickableAchievements.setAdapter(new AchievementAdapter(achievements, new AchievementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Achievement achievement, int position) {
                if (!profileViewModel.getPickedAchievements().getValue().contains(achievement)) profileViewModel.updatePickedAchievements(achievement, selectedPosition);
            }
        }));
    }
}
