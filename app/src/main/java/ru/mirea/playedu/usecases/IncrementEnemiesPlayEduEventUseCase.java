package ru.mirea.playedu.usecases;

import java.util.ArrayList;

import ru.mirea.playedu.data.repository.PlayEduTaskRepository;
import ru.mirea.playedu.model.PlayEduEventKillEnemy;
import ru.mirea.playedu.model.PlayEduTask;

public class IncrementEnemiesPlayEduEventUseCase {
    private PlayEduTaskRepository playEduTaskRepository;

    public IncrementEnemiesPlayEduEventUseCase(PlayEduTaskRepository playEduTaskRepository) {
        this.playEduTaskRepository = playEduTaskRepository;
    }

    public void execute() {
        ArrayList<PlayEduTask> playEduTasks = playEduTaskRepository.getTasks();
        for (PlayEduTask playEduTask : playEduTasks) {
            if (playEduTask.getEvent().getClass() == PlayEduEventKillEnemy.class) {
                PlayEduEventKillEnemy playEduEventKillEnemy = (PlayEduEventKillEnemy) playEduTask.getEvent();
                playEduEventKillEnemy.setEnemiesProgress(playEduEventKillEnemy.getEnemiesProgress() + 1);
                playEduTask.setEvent(playEduEventKillEnemy);
                playEduTaskRepository.updateTask(playEduTasks.indexOf(playEduTask), playEduTask);
            }
        }
    }
}
