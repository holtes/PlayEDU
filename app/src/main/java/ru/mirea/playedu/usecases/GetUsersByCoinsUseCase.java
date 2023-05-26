package ru.mirea.playedu.usecases;

import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;

import ru.mirea.playedu.data.repository.UserRepository;
import ru.mirea.playedu.model.User;

public class GetUsersByCoinsUseCase {
    private UserRepository userRepository;

    public GetUsersByCoinsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<User> execute(int usersCount) {
        ArrayList<User> newUsers = new ArrayList<>();
        ArrayList<User> users = userRepository.getUsers();
        users.sort(new CoinsComp());
        for (int i = 0; i < usersCount; i++) {
            if (i == users.size()) break;
            newUsers.add(users.get(i));
        }
        return newUsers;
    }

    class CoinsComp implements Comparator<User> {
        @Override
        public int compare(User obj1, User obj2) {
            // получаем монетки пользователей
            int c1 = obj1.getGoldenCoins();
            int c2 = obj2.getGoldenCoins();
            // и сравниваем их
            return Integer.compare(c2, c1);
        }
    }
}
