package ru.mirea.playedu.model;

public class PlayEduEventKillEnemy extends PlayEduEvent{

    private int enemiesCount;
    private int enemiesProgress = 0;

    public PlayEduEventKillEnemy(int eventId, String description, int enemiesCount) {
        super(eventId, description);
        this.enemiesCount = enemiesCount;
    }

    public int getEnemiesCount() {
        return enemiesCount;
    }

    public void setEnemiesCount(int enemiesCount) {
        this.enemiesCount = enemiesCount;
    }

    public int getEnemiesProgress() {
        return enemiesProgress;
    }

    public void setEnemiesProgress(int enemiesProgress) {
        this.enemiesProgress = enemiesProgress;
    }
}
