package com.hrm.hrm.Bean;

/**
 * Created by Ishan Puranik on 09/11/2017.
 */

public class DashBean {


    String birthDayCount;
    String holidaysCount;
    String announcementsCount;
    String newsCount;
    String newsLettersCount;
    String guidelinesCount;

    public String getBirthDayCount() {
        return birthDayCount;
    }

    public void setBirthDayCount(String birthDayCount) {
        this.birthDayCount = birthDayCount;
    }

    public String getHolidaysCount() {
        return holidaysCount;
    }

    public void setHolidaysCount(String holidaysCount) {
        this.holidaysCount = holidaysCount;
    }

    public String getAnnouncementsCount() {
        return announcementsCount;
    }

    public void setAnnouncementsCount(String announcementsCount) {
        this.announcementsCount = announcementsCount;
    }

    public String getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(String newsCount) {
        this.newsCount = newsCount;
    }

    public String getNewsLettersCount() {
        return newsLettersCount;
    }

    public void setNewsLettersCount(String newsLettersCount) {
        this.newsLettersCount = newsLettersCount;
    }

    public String getGuidelinesCount() {
        return guidelinesCount;
    }

    public void setGuidelinesCount(String guidelinesCount) {
        this.guidelinesCount = guidelinesCount;
    }

    public DashBean(String birthDayCount, String holidaysCount,
                    String announcementsCount, String newsCount,
                    String newsLettersCount, String guidelinesCount) {
        
        this.birthDayCount = birthDayCount;
        this.holidaysCount = holidaysCount;
        this.announcementsCount = announcementsCount;
        this.newsCount = newsCount;
        this.newsLettersCount = newsLettersCount;
        this.guidelinesCount = guidelinesCount;
    }
}
