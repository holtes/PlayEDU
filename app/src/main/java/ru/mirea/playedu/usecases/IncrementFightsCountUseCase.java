package ru.mirea.playedu.usecases;

import ru.mirea.playedu.data.repository.UserStatsRepository;
import ru.mirea.playedu.model.UserStats;

public class IncrementFightsCountUseCase {
    private UserStatsRepository userStatsRepository;

    public IncrementFightsCountUseCase(UserStatsRepository userStatsRepository) {
        this.userStatsRepository = userStatsRepository;
    }

    public void execute() {
        UserStats userStats = userStatsRepository.getUserStats();
        userStats.setFightsCount(userStats.getFightsCount() + 1);
        userStatsRepository.setUserStats(userStats);
    }
}
