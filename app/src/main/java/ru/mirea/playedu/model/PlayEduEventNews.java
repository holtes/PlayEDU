package ru.mirea.playedu.model;

public class PlayEduEventNews extends PlayEduEvent{
    String url;

    public PlayEduEventNews(int eventId, String description, String url) {
        super(eventId, description);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
