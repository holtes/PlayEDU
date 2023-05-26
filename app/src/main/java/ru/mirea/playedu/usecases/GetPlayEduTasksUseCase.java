package ru.mirea.playedu.usecases;

import java.util.ArrayList;

import ru.mirea.playedu.data.repository.PlayEduTaskRepository;
import ru.mirea.playedu.model.PlayEduTask;

public class GetPlayEduTasksUseCase {
    PlayEduTaskRepository playEduTaskRepository;

    public GetPlayEduTasksUseCase(PlayEduTaskRepository playEduTaskRepository) {
        this.playEduTaskRepository = playEduTaskRepository;
    }

    public ArrayList<PlayEduTask> execute() {
        return playEduTaskRepository.getTasks();
    }
}
