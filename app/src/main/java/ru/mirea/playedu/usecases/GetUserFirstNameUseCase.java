package ru.mirea.playedu.usecases;

import ru.mirea.playedu.data.repository.UserRepository;
import ru.mirea.playedu.model.User;

public class GetUserFirstNameUseCase {
    private UserRepository userRepository;

    public GetUserFirstNameUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String execute() {
        User user = userRepository.getUser();
        String firstName = user.getLogin().split(" ")[1];
        return firstName;
    }
}
