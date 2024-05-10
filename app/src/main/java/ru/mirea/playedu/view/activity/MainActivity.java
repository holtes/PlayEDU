package ru.mirea.playedu.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Calendar;

import ru.mirea.playedu.Constants;
import ru.mirea.playedu.data.storage.cache.AchievementCacheStorage;
import ru.mirea.playedu.data.storage.cache.PlayEduTaskCacheStorage;
import ru.mirea.playedu.data.storage.cache.PowerCacheStorage;
import ru.mirea.playedu.data.storage.cache.UserCacheStorage;
import ru.mirea.playedu.data.storage.cache.UserStatsCacheStorage;
import ru.mirea.playedu.model.Achievement;
import ru.mirea.playedu.model.PlayEduEvent;
import ru.mirea.playedu.model.PlayEduEventKillEnemy;
import ru.mirea.playedu.model.PlayEduEventNews;
import ru.mirea.playedu.model.PlayEduTask;
import ru.mirea.playedu.model.Power;
import ru.mirea.playedu.model.Quiz;
import ru.mirea.playedu.model.User;
import ru.mirea.playedu.model.UserStats;
import ru.mirea.playedu.R;
import ru.mirea.playedu.databinding.ActivityMainBinding;

// Базовая активность
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        navHostFragment.getChildFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if ((int) navController.getCurrentDestination().getId() == 2131361880) {
                    binding.bottomNavigationView.setVisibility(View.GONE);
                }
                else {
                    binding.bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });

        // Перевiрка регистрации
        UserStatsCacheStorage userStatsCacheStorage = UserStatsCacheStorage.getInstance();
        UserCacheStorage userCacheStorage = UserCacheStorage.getInstance();

        User user = userCacheStorage.getUser();
        UserStats userStats = userStatsCacheStorage.getUserStats();

        Log.d("Cat", user.toString() + " " + userStats.toString());

        AchievementCacheStorage cacheStorage = AchievementCacheStorage.getInstance();
        for (Achievement achievement: Constants.getAchievementsList(this)) {
            cacheStorage.addAchievement(achievement);
        }

        PowerCacheStorage cacheStorage1 = PowerCacheStorage.getInstance();
        for (Power power: Constants.getPowersList(this)) {
            cacheStorage1.addPower(power);
        }

        // Мокаем задания от системы (осуждаю)
        JSONArray jsonObject;
        try {
            jsonObject = new JSONArray(readFromAsset());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Calendar date2 = Calendar.getInstance();
        date2.add(Calendar.DAY_OF_MONTH, 1);
        PlayEduTaskCacheStorage playEduTaskCacheStorage = PlayEduTaskCacheStorage.getInstance();
        PlayEduEventKillEnemy playEduEventKillEnemy = new PlayEduEventKillEnemy(0, "Группу моснтров видели возле корпуса Е. Кто знает, что они замышляют. Избавтесь от них!", 10);
        PlayEduTask playEduTask1 = new PlayEduTask(0, "Убить 10 монстров в режиме приключения", playEduEventKillEnemy, false, 50, Calendar.getInstance().getTime(), date2.getTime());
        PlayEduEventNews playEduEventNews = new PlayEduEventNews(0, "Интересно, а как живут маги в Арктике? Думаю, что стоит приоткрыть завесу тайны!", "https://vk.com/mirea_official?w=wall-1388_33846");
        PlayEduTask playEduTask2 = new PlayEduTask(0, "Посмотреть запись волонтерского центра", playEduEventNews, false, 80, Calendar.getInstance().getTime(), date2.getTime());
        Quiz quiz = new Quiz(0, "Квиз на тему Разработка мобильных приложений", jsonObject);
        PlayEduTask playEduTask3 = new PlayEduTask(0, "Квиз на тему Разработка мобильных приложений", quiz, false, 80, Calendar.getInstance().getTime(), date2.getTime());
        playEduTaskCacheStorage.addTask(playEduTask1);
        playEduTaskCacheStorage.addTask(playEduTask2);
        playEduTaskCacheStorage.addTask(playEduTask3);
        setContentView(binding.getRoot());
    }

    private String readFromAsset() {
        String string = "";

        try {
            InputStream inputStream = getAssets().open("QuizMok.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            string = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }
}