package ru.mirea.playedu.usecases;

import java.util.ArrayList;

import ru.mirea.playedu.data.repository.UserRepository;
import ru.mirea.playedu.model.Achievement;
import ru.mirea.playedu.model.User;

public class UpdateProfileAchievementsUseCase {
    private UserRepository userRepository;

    public UpdateProfileAchievementsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(ArrayList<Achievement> pickedAchievements) {
        User user = userRepository.getUser();
        user.setProfileAchievements(pickedAchievements);
        userRepository.updateUser(user);
    }
}
