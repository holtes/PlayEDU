package ru.mirea.playedu.usecases;

import java.util.ArrayList;

import ru.mirea.playedu.data.repository.PlayEduTaskRepository;
import ru.mirea.playedu.data.repository.UserRepository;
import ru.mirea.playedu.model.PlayEduEventKillEnemy;
import ru.mirea.playedu.model.PlayEduTask;
import ru.mirea.playedu.model.User;

public class CheckCompletedPlayEduTasksUseCase {
    PlayEduTaskRepository playEduTaskRepository;
    UserRepository userRepository;

    public CheckCompletedPlayEduTasksUseCase(PlayEduTaskRepository playEduTaskRepository, UserRepository userRepository) {
        this.playEduTaskRepository = playEduTaskRepository;
        this.userRepository = userRepository;
    }

    public void execute() {
        ArrayList<PlayEduTask> playEduTasks = playEduTaskRepository.getTasks();
        ArrayList<PlayEduTask> tasksToDelete = new ArrayList<>();
        User user = userRepository.getUser();
        for (PlayEduTask playEduTask : playEduTasks) {
            if (playEduTask.getEvent().getClass() == PlayEduEventKillEnemy.class) {
                PlayEduEventKillEnemy playEduEventKillEnemy = (PlayEduEventKillEnemy) playEduTask.getEvent();
                if (playEduEventKillEnemy.getEnemiesProgress() >= playEduEventKillEnemy.getEnemiesCount()) {
                    user.setGoldenCoins(user.getGoldenCoins() + playEduTask.getCoinsReward());
                    tasksToDelete.add(playEduTask);
                    userRepository.updateUser(user);
                }
            }
        }
        for (PlayEduTask playEduTask : tasksToDelete) {
            playEduTaskRepository.deleteTask(playEduTask);
        }
    }
}
