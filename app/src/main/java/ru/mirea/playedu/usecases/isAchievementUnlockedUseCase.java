package ru.mirea.playedu.usecases;

import android.util.Log;

import ru.mirea.playedu.data.repository.AchievementRepository;
import ru.mirea.playedu.data.repository.UserStatsRepository;
import ru.mirea.playedu.model.Achievement;
import ru.mirea.playedu.model.UserStats;

public class isAchievementUnlockedUseCase {
    private UserStatsRepository userStatsRepository;
    private AchievementRepository achievementRepository;
    private Achievement targetAchievement;
    private GetSellingPowersUseCase getSellingPowersUseCase;

    public isAchievementUnlockedUseCase(UserStatsRepository userStatsRepository, AchievementRepository achievementRepository, GetSellingPowersUseCase getSellingPowersUseCase) {
        this.userStatsRepository = userStatsRepository;
        this.achievementRepository = achievementRepository;
        this.getSellingPowersUseCase = getSellingPowersUseCase;
    }

    public boolean execute(Achievement achievement) {
        UserStats userStats = userStatsRepository.getUserStats();
        targetAchievement = achievement;
        switch (achievement.getAchievementId()) {
            case 0:
                if (userStats.getFightsCount() == 1) {
                    unlockAchievement(0);
                    return true;
                }
                break;
            case 1:
                if (userStats.getPlayEduTasksCompleted() == 1) {
                    unlockAchievement(1);
                    return true;
                }
                break;
            case 2:
                if (getSellingPowersUseCase.execute().size() == 0) {
                    unlockAchievement(2);
                    return true;
                }
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                if (userStats.getEnemiesKilled() >= 10) {
                    unlockAchievement(7);
                    return true;
                }
                break;
            case 8:
                if (userStats.getEnemiesKilled() >= 50) {
                    unlockAchievement(8);
                    return true;
                }
                break;
            case 9:
                if (userStats.getEnemiesKilled() >= 100) {
                    unlockAchievement(9);
                    return true;
                }
                break;
            case 10:
                if (userStats.getEnemiesKilled() >= 1000) {
                    unlockAchievement(10);
                    return true;
                }
                break;
            case 11:
                if (userStats.getPlayEduTasksCompleted() >= 10) {
                    unlockAchievement(11);
                    return true;
                }
                break;
            case 12:
                if (userStats.getPlayEduTasksCompleted() >= 50) {
                    unlockAchievement(12);
                    return true;
                }
                break;
            case 13:
                if (userStats.getPlayEduTasksCompleted() >= 100) {
                    unlockAchievement(13);
                    return true;
                }
                break;
            case 14:
                if (userStats.getPlayEduTasksCompleted() >= 1000) {
                    unlockAchievement(14);
                    return true;
                }
                break;
            case 15:
                if (userStats.getUserTasksCompleted() >= 10) {
                    unlockAchievement(15);
                    return true;
                }
                break;
            case 16:
                if (userStats.getUserTasksCompleted() >= 50) {
                    unlockAchievement(16);
                    return true;
                }
                break;
            case 17:
                if (userStats.getUserTasksCompleted() >= 100) {
                    unlockAchievement(17);
                    return true;
                }
                break;
            case 18:
                if (userStats.getUserTasksCompleted() >= 1000) {
                    unlockAchievement(18);
                    return true;
                }
                break;
        }
        return false;
    }

    private void unlockAchievement(int id) {
        targetAchievement.setUnlocked(true);
        achievementRepository.updateAchievement(id, targetAchievement);
    }


}
