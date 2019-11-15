package com.hrm.hrm.Bean;

/**
 * Created by Ishan Puranik on 15/11/2017.
 */

public class AchievementsBean {

    String achievementName;
    String achievementDate;
    String achievementRemark;

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public String getAchievementDate() {
        return achievementDate;
    }

    public void setAchievementDate(String achievementDate) {
        this.achievementDate = achievementDate;
    }

    public String getAchievementRemark() {
        return achievementRemark;
    }

    public void setAchievementRemark(String achievementRemark) {
        this.achievementRemark = achievementRemark;
    }

    public AchievementsBean(String achievementName, String achievementDate, String achievementRemark) {

        this.achievementName = achievementName;
        this.achievementDate = achievementDate;
        this.achievementRemark = achievementRemark;
    }
}
