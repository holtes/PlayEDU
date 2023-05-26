package ru.mirea.playedu.usecases;

import java.util.ArrayList;
import java.util.Calendar;

import ru.mirea.playedu.DateHelper;
import ru.mirea.playedu.data.repository.PlayEduTaskRepository;
import ru.mirea.playedu.data.repository.UserTaskRepository;
import ru.mirea.playedu.model.PlayEduTask;
import ru.mirea.playedu.model.UserTask;

// Получение задач с заданной датой создания
public class GetTasksWithCreationDatePlayEduUseCase {

    private PlayEduTaskRepository playEduTaskRepository;

    public GetTasksWithCreationDatePlayEduUseCase(PlayEduTaskRepository playEduTaskRepository) {
        this.playEduTaskRepository = playEduTaskRepository;
    }

    public ArrayList<PlayEduTask> execute(ArrayList<PlayEduTask> tasks, Calendar date) {
        ArrayList<PlayEduTask> filteredTasks = new ArrayList<>();
        for (PlayEduTask userTask : tasks) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(userTask.getCreationDate());
            if (DateHelper.equalDate(date, calendar)) {
                filteredTasks.add(userTask);
            }
        }
        return filteredTasks;
    }
}
