package ru.mirea.playedu.viewmodel;

import static ru.mirea.playedu.Constants.LESS;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import ru.mirea.playedu.data.repository.UserRepository;
import ru.mirea.playedu.data.repository.UserStatsRepository;
import ru.mirea.playedu.data.storage.cache.UserCacheStorage;
import ru.mirea.playedu.data.storage.cache.UserStatsCacheStorage;
import ru.mirea.playedu.model.User;
import ru.mirea.playedu.model.UserStats;
import ru.mirea.playedu.usecases.GetUserStatsUseCase;
import ru.mirea.playedu.usecases.GetUsersByCoinsUseCase;

public class CommunityViewModel extends ViewModel {

    private GetUserStatsUseCase getUserStatsUseCase;
    private GetUsersByCoinsUseCase getUsersByCoinsUseCase;
    private MutableLiveData<UserStats> userStats;
    private MutableLiveData<Integer> usersCount;

    public CommunityViewModel() {
        this.getUserStatsUseCase = new GetUserStatsUseCase(new
                UserStatsRepository(UserStatsCacheStorage.getInstance()));
        UserRepository userRepository = new UserRepository(UserCacheStorage.getInstance());
        getUsersByCoinsUseCase = new GetUsersByCoinsUseCase(userRepository);
        userStats = new MutableLiveData<>();
        usersCount = new MutableLiveData<>();
        userStats.setValue(getUserStatsUseCase.execute());
        usersCount.setValue(LESS);

    }

    public void setUsersCount(int count) {
        usersCount.setValue(count);
    }

    public LiveData<Integer> getUsersCount() {
        return usersCount;
    }

    public LiveData<UserStats> getUserStats() {
        return userStats;
    }

    public ArrayList<User> getUsersByCoins() {
        return getUsersByCoinsUseCase.execute(usersCount.getValue());
    }





}
