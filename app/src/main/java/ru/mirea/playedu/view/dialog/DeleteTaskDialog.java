package ru.mirea.playedu.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.playedu.R;
import ru.mirea.playedu.databinding.DialogCompleteTaskBinding;
import ru.mirea.playedu.databinding.DialogDeleteTaskBinding;
import ru.mirea.playedu.model.UserTask;
import ru.mirea.playedu.viewmodel.TasksViewModel;

public class DeleteTaskDialog extends DialogFragment {

    private View view;
    private UserTask userTask;

    public DeleteTaskDialog(UserTask task) {
        this.userTask = task;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = requireActivity().getLayoutInflater().inflate(
                R.layout.dialog_delete_task,
                null,
                false);

        // Фон диалога
        getDialog().getWindow().setBackgroundDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.shape_dialog));
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DialogDeleteTaskBinding binding = DialogDeleteTaskBinding.inflate(getLayoutInflater());
        TasksViewModel viewModel = new ViewModelProvider(requireActivity()).get(TasksViewModel.class);

        binding.yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteUserTask(userTask);
                dismiss();
            }
        });

        binding.noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        builder.setView(binding.getRoot());
        return builder.create();
    }

}
