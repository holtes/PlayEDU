package ru.mirea.playedu.usecases;

import ru.mirea.playedu.data.repository.UserStatsRepository;
import ru.mirea.playedu.model.UserStats;

public class IncrementKilledEnemiesUseCase {
    private UserStatsRepository userStatsRepository;

    public IncrementKilledEnemiesUseCase(UserStatsRepository userStatsRepository) {
        this.userStatsRepository = userStatsRepository;
    }

    public void execute() {
        UserStats userStats = userStatsRepository.getUserStats();
        userStats.setEnemiesKilled(userStats.getEnemiesKilled() + 1);
        userStatsRepository.setUserStats(userStats);
    }
}
