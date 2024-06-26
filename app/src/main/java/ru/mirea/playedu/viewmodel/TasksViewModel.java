package ru.mirea.playedu.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.mirea.playedu.data.repository.CategoryRepository;
import ru.mirea.playedu.data.repository.PlayEduTaskRepository;
import ru.mirea.playedu.data.repository.UserRepository;
import ru.mirea.playedu.data.repository.UserStatsRepository;
import ru.mirea.playedu.data.repository.UserTaskRepository;
import ru.mirea.playedu.data.storage.cache.CategoryCacheStorage;
import ru.mirea.playedu.data.storage.cache.PlayEduTaskCacheStorage;
import ru.mirea.playedu.data.storage.cache.UserCacheStorage;
import ru.mirea.playedu.data.storage.cache.UserStatsCacheStorage;
import ru.mirea.playedu.data.storage.cache.UserTaskCacheStorage;
import ru.mirea.playedu.model.PlayEduTask;
import ru.mirea.playedu.model.Response;
import ru.mirea.playedu.model.User;
import ru.mirea.playedu.model.UserTask;
import ru.mirea.playedu.model.UserTaskFilter;
import ru.mirea.playedu.usecases.CheckCompletedPlayEduTasksUseCase;
import ru.mirea.playedu.usecases.CompletePlayEduTaskUseCase;
import ru.mirea.playedu.usecases.CompleteUserTaskUseCase;
import ru.mirea.playedu.usecases.DeleteUserTaskUseCase;
import ru.mirea.playedu.usecases.GetCategoryTitlesListUseCase;
import ru.mirea.playedu.usecases.GetPlayEduTasksUseCase;
import ru.mirea.playedu.usecases.GetTasksWithCategoryUseCase;
import ru.mirea.playedu.usecases.GetTasksWithColorUseCase;
import ru.mirea.playedu.usecases.GetTasksWithCreationDatePlayEduUseCase;
import ru.mirea.playedu.usecases.GetTasksWithCreationDateUseCase;
import ru.mirea.playedu.usecases.GetUserFirstNameUseCase;
import ru.mirea.playedu.usecases.GetUserTasksListUseCase;
import ru.mirea.playedu.usecases.GetUserUseCase;
import ru.mirea.playedu.view.dialog.DeleteTaskDialog;
import ru.mirea.playedu.view.dialog.PlayEduTaskDialog;

public class TasksViewModel extends ViewModel {

    // Используемые View-Model use-кейсы
    private GetCategoryTitlesListUseCase getCategoryTitlesListUseCase;
    private GetTasksWithCategoryUseCase getTasksWithCategoryUseCase;
    private GetTasksWithColorUseCase getTasksWithColorUseCase;
    private GetTasksWithCreationDateUseCase getTasksWithCreationDateUseCase;
    private GetTasksWithCreationDatePlayEduUseCase getTasksWithCreationDatePlayEduUseCase;
    private GetUserTasksListUseCase getUserTasksListUseCase;
    private CompleteUserTaskUseCase completeUserTaskUseCase;
    private CompletePlayEduTaskUseCase completePlayEduTaskUseCase;
    private DeleteUserTaskUseCase deleteUserTaskUseCase;
    private GetUserUseCase getUserUseCase;
    private GetUserFirstNameUseCase getUserFirstNameUseCase;
    public GetPlayEduTasksUseCase getPlayEduTasksUseCase;
    public CheckCompletedPlayEduTasksUseCase checkCompletedPlayEduTasksUseCase;
    // Список пользовательских задач
    private final MutableLiveData<ArrayList<UserTask>> userTasksList = new MutableLiveData<>();;
    // Список заданий от системы
    private final MutableLiveData<ArrayList<PlayEduTask>> playEduTasksList = new MutableLiveData<>();;
    // Список категорий
    private final MutableLiveData<ArrayList<String>> categoryTitlesList = new MutableLiveData<>();
    // Массив дат
    private ArrayList<Calendar> dateList;
    // Индекс текущей даты в массиве дат
    private int todayDatePosition;
    private int currentDatePosition;
    private UserTaskFilter userTaskFilter;
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<User> user = new MutableLiveData<>();


    public TasksViewModel() {
        UserTaskRepository userTaskRepository = new UserTaskRepository(UserTaskCacheStorage.getInstance());
        CategoryRepository categoryRepository = new CategoryRepository(CategoryCacheStorage.getInstance());
        UserStatsRepository statsRepository = new UserStatsRepository(UserStatsCacheStorage.getInstance());
        UserRepository userRepository = new UserRepository(UserCacheStorage.getInstance());
        PlayEduTaskRepository playEduTaskRepository = new PlayEduTaskRepository(PlayEduTaskCacheStorage.getInstance());

        getCategoryTitlesListUseCase = new GetCategoryTitlesListUseCase(categoryRepository);
        getTasksWithCategoryUseCase = new GetTasksWithCategoryUseCase(userTaskRepository);
        getTasksWithColorUseCase = new GetTasksWithColorUseCase(userTaskRepository);
        getTasksWithCreationDateUseCase = new GetTasksWithCreationDateUseCase(userTaskRepository);
        getTasksWithCreationDatePlayEduUseCase = new GetTasksWithCreationDatePlayEduUseCase(playEduTaskRepository);
        getUserTasksListUseCase = new GetUserTasksListUseCase(userTaskRepository);
        completeUserTaskUseCase = new CompleteUserTaskUseCase(userTaskRepository, statsRepository, userRepository);
        getUserUseCase = new GetUserUseCase(userRepository);
        getUserFirstNameUseCase = new GetUserFirstNameUseCase(userRepository);
        deleteUserTaskUseCase = new DeleteUserTaskUseCase(userTaskRepository);
        getPlayEduTasksUseCase = new GetPlayEduTasksUseCase(playEduTaskRepository);
        completePlayEduTaskUseCase = new CompletePlayEduTaskUseCase(playEduTaskRepository, userRepository);
        checkCompletedPlayEduTasksUseCase = new CheckCompletedPlayEduTasksUseCase(playEduTaskRepository, userRepository);
        dateList = new ArrayList<>();
        userTaskFilter = new UserTaskFilter();

        userTasksList.setValue(new ArrayList<>());
        playEduTasksList.setValue(new ArrayList<>());
        categoryTitlesList.setValue(new ArrayList<>());
        getData();
    }

    private void getData() {
        user.setValue(getUserUseCase.execute());
        fillDateList();
        userTaskFilter.setFilteredDate(dateList.get(todayDatePosition));
        filterUserTasks();
        categoryTitlesList.setValue(getCategoryTitlesListUseCase.execute());

    }

    // Фильтрует список пользовательских задач по заданной категории
    public void filterUserTasksByCategory(String category) {
        userTaskFilter.setFilteredCategory(category);
        userTaskFilter.setFilteredColor(null);
        filterUserTasks();
    }

    // Фильтрует список пользовательских задач по заданному цвету
    public void filterUserTasksByColor(int color) {
        userTaskFilter.setFilteredColor(color);
        userTaskFilter.setFilteredCategory(null);
        filterUserTasks();
    }

    // Фильтрует список пользовательских задач по заданной дате создания
    public void filterUserTasksByDate(Calendar date) {
        currentDatePosition = dateList.indexOf(date);
        userTaskFilter.setFilteredDate(date);
        filterUserTasks();
    }

    // Формирует список из всех пользовательских задач и фильтрует его по заданной параметрам
    private void filterUserTasks() {
        if (userTaskFilter.getFilteredDate() != null) {
            userTasksList.setValue(getTasksWithCreationDateUseCase.
                    execute(getUserTasksListUseCase.execute(), userTaskFilter.getFilteredDate()));
        }
        if (userTaskFilter.getFilteredCategory() != null) {
            userTasksList.setValue(getTasksWithCategoryUseCase.execute(userTasksList.getValue(), userTaskFilter.getFilteredCategory()));
        }
        if (userTaskFilter.getFilteredColor() != null) {
            userTasksList.setValue(getTasksWithColorUseCase.execute(userTasksList.getValue(), userTaskFilter.getFilteredColor()));
        }
    }

    public void filterPlayEduTasksByDate(Calendar date) {
        playEduTasksList.setValue(getTasksWithCreationDatePlayEduUseCase.execute(getPlayEduTasksUseCase.execute(), date));
    }

    private void fillDateList() {
        Date today = Calendar.getInstance().getTime();
        // Получение даты, равной текущей минус год
        Calendar yearAgo = Calendar.getInstance();
        yearAgo.add(Calendar.YEAR, -1);
        // Получение даты, равной текущей плюс год
        Calendar yearLater = Calendar.getInstance();
        yearLater.add(Calendar.YEAR, +1);

        for (Calendar date = yearAgo;
             date.getTime().getTime() <= yearLater.getTime().getTime();
             date.add(Calendar.DAY_OF_MONTH, 1)) {
            Calendar temp = (Calendar) date.clone();
            dateList.add(temp);
            if (temp.getTime().getTime() == today.getTime())
                todayDatePosition = dateList.size() - 1;
        }
        currentDatePosition = todayDatePosition;
    }

    /**
     Метод завершает задание, созданное игроком
     @param  task  Задание, которое нужно завершить
     *
     @return   int   Код ответа
     *
     @see         UserTask
     */
    public int completeUserTask(UserTask task) {
        Response response = completeUserTaskUseCase.execute(task);
        if (response.getCode() != 200) {
            errorMessage.setValue(response.getBody());
        } else {
            filterUserTasks();
            user.setValue(getUserUseCase.execute());
        }
        return response.getCode();

    }

    public int completePlayEduTask(PlayEduTask playEduTask) {
        Response response = completePlayEduTaskUseCase.execute(playEduTask);
        if (response.getCode() != 200) {
            errorMessage.setValue(response.getBody());
        }
        else {
            user.setValue(getUserUseCase.execute());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(playEduTask.getCreationDate());
            filterPlayEduTasksByDate(calendar);
        }
        return response.getCode();
    }

    public int deleteUserTask(UserTask task) {
        Response response = deleteUserTaskUseCase.execute(task);
        if (response.getCode() != 200) {
            errorMessage.setValue(response.getBody());
        } else {
            filterUserTasks();
        }
        return response.getCode();
    }

    public LiveData<ArrayList<UserTask>> getUserTasksList() {
        return userTasksList;
    }

    public MutableLiveData<ArrayList<String>> getCategoryTitlesList() {
        categoryTitlesList.setValue(getCategoryTitlesListUseCase.execute());
        return categoryTitlesList;
    }

    // Заполнение массива дат в диапазоне (-год от сегодняшней даты; +год от сегодняшней)


    public ArrayList<Calendar> getDateList() {
        return dateList;
    }

    public int getTodayDayPosition() {
        return todayDatePosition;
    }

    public UserTaskFilter getUserTaskFilter() {
        return userTaskFilter;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public int getCurrentDatePosition() {
        return currentDatePosition;
    }

    public String getUserFirstName() {
        return getUserFirstNameUseCase.execute();
    }

    public LiveData<ArrayList<PlayEduTask>> getPlayEduTasksList() {
        return playEduTasksList;
    }

    public void checkCompletedPlayEduTasks() {
        filterPlayEduTasksByDate(dateList.get(currentDatePosition));
        checkCompletedPlayEduTasksUseCase.execute();
    }
}
