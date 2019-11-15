package com.hrm.hrm.Bean;

/**
 * Created by Ishan Puranik on 17/11/2017.
 */

public class NewsBean {
    String nId;
    String news;
    String title;
    String date;
    String status;

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NewsBean(String nId, String news, String title, String date, String status) {
        this.nId = nId;
        this.news = news;
        this.title = title;
        this.date = date;
        this.status = status;
    }
}
