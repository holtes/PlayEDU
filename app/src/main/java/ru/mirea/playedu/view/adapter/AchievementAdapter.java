package ru.mirea.playedu.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.mirea.playedu.R;
import ru.mirea.playedu.databinding.ViewAchievementItemBinding;
import ru.mirea.playedu.model.Achievement;

// Адаптер для отображения списка достижений
public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Achievement achievement, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Achievement achievement);
    }

    // Список достижений
    private ArrayList<Achievement> achievements;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

    public AchievementAdapter(ArrayList<Achievement> achievements, OnItemClickListener clickListener) {
        this.achievements = achievements;
        this.clickListener = clickListener;
    }

    public AchievementAdapter(ArrayList<Achievement> achievements, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        this.achievements = achievements;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    public void setAchievementsList(ArrayList<Achievement> list) {
        this.achievements = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AchievementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewAchievementItemBinding binding = ViewAchievementItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new AchievementAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(achievements.get(position), clickListener, longClickListener, position);
    }


    @Override
    public int getItemCount() {
        return achievements.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ViewAchievementItemBinding binding;

        public ViewHolder(ViewAchievementItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Achievement achievement, OnItemClickListener listener, OnItemLongClickListener longClickListener, int position) {

            binding.setAchievement(achievement);
            binding.powerImg.setImageResource(achievement.getIcon());
            if (!achievement.isUnlocked()) {
                binding.powerImg.setForeground(itemView.getResources().getDrawable(R.drawable.shape_power_overlay));
            }
            else
                binding.powerImg.setForeground(null);
            itemView.setOnClickListener(view -> listener.onItemClick(achievement, position));
            if (longClickListener != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        longClickListener.onItemLongClick(achievement);
                        return false;
                    }
                });
            }
        }

    }


}

