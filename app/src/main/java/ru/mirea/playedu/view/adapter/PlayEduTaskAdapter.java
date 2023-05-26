package ru.mirea.playedu.view.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import ru.mirea.playedu.Constants;
import ru.mirea.playedu.R;
import ru.mirea.playedu.model.PlayEduTask;
import ru.mirea.playedu.model.UserTask;

// Адаптер для отображения юзерских задач
public class PlayEduTaskAdapter extends RecyclerView.Adapter<PlayEduTaskAdapter.ViewHolder> {

    public interface TaskItemListener {
        void onClick(PlayEduTask playEduTask);
    }

    // Список юзерских задач,
    private ArrayList<PlayEduTask> tasks;
    private TaskItemListener taskItemListener;


    public PlayEduTaskAdapter(ArrayList<PlayEduTask> tasks, TaskItemListener taskItemListener) {
        this.tasks = tasks;
        this.taskItemListener = taskItemListener;
    }

    @NonNull
    @Override
    public PlayEduTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_task, parent, false);
        return new PlayEduTaskAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayEduTaskAdapter.ViewHolder holder, int position) {
        holder.bind(tasks.get(position), taskItemListener);
    }


    // Возвращает количество задач
    @Override
    public int getItemCount() {
        return tasks.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView labelTxt;
        private TextView deadlineTxt;
        private TextView rewardTxt;
        private LinearLayout background;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            labelTxt = itemView.findViewById(R.id.label_txt);
            rewardTxt = itemView.findViewById(R.id.price_txt);
            deadlineTxt = itemView.findViewById(R.id.deadline_txt);
            background = itemView.findViewById(R.id.linearLayout3);
        }

        public void bind(PlayEduTask task, TaskItemListener listener) {
            labelTxt.setText(task.getLabel());
            rewardTxt.setText(Integer.toString(task.getCoinsReward()));
            deadlineTxt.setText(Constants.getDeadlineString(task.getDeadlineDate()));
            Calendar calendar = Calendar.getInstance();
            if (task.getDeadlineDate().after(calendar.getTime())) {
                deadlineTxt.setTextColor(Color.RED);
            }

            background.setOnClickListener(view -> {
                listener.onClick(task);
            });

        }


    }


}
