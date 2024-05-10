package ru.mirea.playedu.view.adapter;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.mirea.playedu.R;
import ru.mirea.playedu.callbacks.OnSelectColorFilterCallback;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {
    private ArrayList<String> answersTexts;
    public interface OnSelectAnswerCallback {
        void OnItemClick(int num);
    };

    private OnSelectAnswerCallback callback;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public Button answerBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            answerBtn = (Button) itemView.findViewById(R.id.answer_btn);
        }
    }

    public AnswersAdapter(ArrayList<String> answersTexts, OnSelectAnswerCallback callback) {
        this.answersTexts = answersTexts;
        this.callback = callback;
    }

    @NonNull
    @Override
    public AnswersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_answer_item, parent, false);
        return new AnswersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersAdapter.ViewHolder holder, int position) {
        holder.answerBtn.setText(answersTexts.get(position));
        holder.answerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.OnItemClick(position + 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return answersTexts.size();
    }
}
