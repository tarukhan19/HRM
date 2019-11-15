package com.hrm.hrm.Bean;

/**
 * Created by Ishan Puranik on 23/11/2017.
 */

public class AnnouncementBean {

    String annId, subject, description, annDate;

    public String getAnnId() {
        return annId;
    }

    public void setAnnId(String annId) {
        this.annId = annId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnnDate() {
        return annDate;
    }

    public void setAnnDate(String annDate) {
        this.annDate = annDate;
    }

    public AnnouncementBean(String annId, String subject, String description, String annDate) {

        this.annId = annId;
        this.subject = subject;
        this.description = description;
        this.annDate = annDate;
    }
}
