package com.hrm.hrm.Bean;

/**
 * Created by Ishan Puranik on 16/11/2017.
 */

public class GuidelinesBean {
    String gId;
    String subject;
    String file_name;

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public GuidelinesBean(String gId, String subject, String file_name) {
        this.gId = gId;
        this.subject = subject;
        this.file_name = file_name;
    }
}
