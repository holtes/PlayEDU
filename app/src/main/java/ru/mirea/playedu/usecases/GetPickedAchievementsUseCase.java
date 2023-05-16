package ru.mirea.playedu.usecases;

import static ru.mirea.playedu.Constants.selectableAchievement;

import java.util.ArrayList;

import ru.mirea.playedu.data.repository.UserRepository;
import ru.mirea.playedu.model.Achievement;
import ru.mirea.playedu.model.User;

public class GetPickedAchievementsUseCase {
    private UserRepository userRepository;

    public GetPickedAchievementsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<Achievement> execute() {
        if (userRepository.getUser().getProfileAchievements().size() == 0) {
            ArrayList<Achievement> selectableAchievements = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                selectableAchievements.add(selectableAchievement);
            }
            User user = userRepository.getUser();
            user.setProfileAchievements(selectableAchievements);
            userRepository.updateUser(user);
        }
        return userRepository.getUser().getProfileAchievements();
    }
}
