package ru.mirea.playedu.usecases;

import ru.mirea.playedu.data.repository.UserTaskRepository;
import ru.mirea.playedu.model.Response;
import ru.mirea.playedu.model.UserTask;

public class DeleteUserTaskUseCase {
    UserTaskRepository userTaskRepository;

    public DeleteUserTaskUseCase(UserTaskRepository userTaskRepository) {
        this.userTaskRepository = userTaskRepository;
    }

    public Response execute(UserTask userTask) {
        Response taskResponse = userTaskRepository.deleteTask(userTask);
        return taskResponse;
    }
}
