package ru.mirea.playedu.data.storage.cache;

import static java.util.stream.Collectors.toCollection;
import static ru.mirea.playedu.Constants.FEMALE_IC;
import static ru.mirea.playedu.Constants.MALE_IC;

import java.util.ArrayList;

import ru.mirea.playedu.data.repository.AchievementRepository;
import ru.mirea.playedu.model.Achievement;
import ru.mirea.playedu.model.User;

// Класс для хранения объекта-пользователя в кэше
// Реализует паттерн Singleton
public class UserCacheStorage {

    private static UserCacheStorage instance = null;

    private User user;

    private UserCacheStorage() {}

    // Возвращает экземпляр класса
    // Если экземпляра нет, создает новый
    public static UserCacheStorage getInstance() {
        if (instance == null)
            instance = new UserCacheStorage();

        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<User> getUsers() {
        // Мокаем пользователей для таблицы лидеров (осуждаю)
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Achievement> achievements = new ArrayList<>();
        AchievementRepository achievementRepository = new AchievementRepository(AchievementCacheStorage.getInstance());
        ArrayList<Achievement> allAchievements = achievementRepository.getAchievements();
        Achievement achievement1 = new Achievement(-1, -1, allAchievements.get(0).getTitle(), allAchievements.get(0).getDescription(), true, allAchievements.get(0).getIcon());
        Achievement achievement2 = new Achievement(-1, -1, allAchievements.get(1).getTitle(), allAchievements.get(1).getDescription(), true, allAchievements.get(1).getIcon());
        Achievement achievement3 = new Achievement(-1, -1, allAchievements.get(2).getTitle(), allAchievements.get(2).getDescription(), true, allAchievements.get(2).getIcon());
        achievements.add(achievement1);
        achievements.add(achievement2);
        achievements.add(achievement3);
        User user1 = new User("Шешуков Леонид Федорович", "passwordCOmrade",
                "+89690776", "ИНБО-01-17", 500, 1000, MALE_IC, achievements);
        User user2 = new User("Пропастин Алексей Федорович", "passwordCOmrade",
                "+89690776", "ИКБО-04-22", 1000, 1000, FEMALE_IC, achievements);
        User user3 = new User("Дроздов Николай Александрович", "passwordCOmrade",
                "+89690776", "ИКБО-04-22", 10, 1000, FEMALE_IC, achievements);
        User user4 = new User("Крупнов Евгений Олегович", "passwordCOmrade",
                "+89690776", "ИКБО-04-22", 5, 1000, MALE_IC, achievements);
        User user5 = new User("Стрешнев Игорь Андреевич", "passwordCOmrade",
                "+89690776", "ИКБО-04-22", 50, 1000, FEMALE_IC, achievements);
        User user6 = new User("Вишневский Максим Андреевич", "passwordCOmrade",
                "+89690776", "ИКБО-04-22", 55, 1000, MALE_IC, achievements);
        User user7 = new User("Гаврилов Дмитрий Сергеевич", "passwordCOmrade",
                "+89690776", "ИКБО-04-22", 15, 1000, FEMALE_IC, achievements);
        User user8 = new User("Гулякин Егор Владимирович", "passwordCOmrade",
                "+89690776", "ИКБО-04-22", 14, 1000, FEMALE_IC, achievements);
        User user9 = new User("Логвинов Артём Александрович", "passwordCOmrade",
                "+89690776", "ИКБО-04-22", 7, 1000, MALE_IC, achievements);
        User user10 = new User("Павлов Кирилл Сергеевич", "passwordCOmrade",
                "+89690776", "ИКБО-04-22", 80, 1000, FEMALE_IC, achievements);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);
        users.add(user8);
        users.add(user9);
        users.add(user10);
        users.add(getUser());
        return users;
    }
}
