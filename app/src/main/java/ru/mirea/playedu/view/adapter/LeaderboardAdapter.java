package ru.mirea.playedu.view.adapter;

import static ru.mirea.playedu.Constants.MALE_IC;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import ru.mirea.playedu.Constants;
import ru.mirea.playedu.R;
import ru.mirea.playedu.model.User;
import ru.mirea.playedu.model.UserTask;

// Адаптер для отображения юзерских задач
public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    public interface UserItemListener {
        void onClick(User user);
    }

    // Список юзерв
    private ArrayList<User> users;
    private UserItemListener userItemListener;


    public LeaderboardAdapter(ArrayList<User> users, UserItemListener userItemListener) {
        this.users = users;
        this.userItemListener = userItemListener;
    }

    @NonNull
    @Override
    public LeaderboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_leaderboard_item, parent, false);
        return new LeaderboardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.ViewHolder holder, int position) {
        holder.bind(users.get(position), userItemListener);
    }


    // Возвращает количество задач
    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView coinsCount;
        private TextView group;
        private TextView login;
        private ImageView icon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            login = itemView.findViewById(R.id.login);
            group = itemView.findViewById(R.id.group);
            coinsCount = itemView.findViewById(R.id.coins_txt);
        }

        public void bind(User user, UserItemListener listener) {

            if (Objects.equals(user.getUserIcon(), MALE_IC)) {
                icon.setImageResource(R.drawable.pic_magician);
            }
            else {
                icon.setImageResource(R.drawable.ic_female_magician);
            }
            login.setText(user.getLogin());
            group.setText(user.getGroup());
            coinsCount.setText(Integer.toString(user.getGoldenCoins()));

            icon.setOnClickListener(view -> {
                listener.onClick(user);
            });
        }


    }


}
