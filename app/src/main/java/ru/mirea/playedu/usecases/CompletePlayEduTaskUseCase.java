package ru.mirea.playedu.usecases;

import ru.mirea.playedu.data.repository.PlayEduTaskRepository;
import ru.mirea.playedu.data.repository.UserRepository;
import ru.mirea.playedu.data.repository.UserTaskRepository;
import ru.mirea.playedu.model.PlayEduTask;
import ru.mirea.playedu.model.Response;
import ru.mirea.playedu.model.User;

public class CompletePlayEduTaskUseCase {
    private PlayEduTaskRepository playEduTaskRepository;
    private UserRepository userRepository;

    public CompletePlayEduTaskUseCase(PlayEduTaskRepository playEduTaskRepository, UserRepository userRepository) {
        this.playEduTaskRepository = playEduTaskRepository;
        this.userRepository = userRepository;
    }

    public Response execute(PlayEduTask playEduTask) {
        User user = userRepository.getUser();
        user.setGoldenCoins(user.getGoldenCoins() + playEduTask.getCoinsReward());
        playEduTaskRepository.deleteTask(playEduTask);
        return userRepository.updateUser(user);
    }
}
