package com.hrm.hrm.Bean;

/**
 * Created by Ishan Puranik on 15/11/2017.
 */

public class HobbiesBean {

    String hobbiName;
    String hobbiRemark;

    public String getHobbiName() {
        return hobbiName;
    }

    public void setHobbiName(String hobbiName) {
        this.hobbiName = hobbiName;
    }

    public String getHobbiRemark() {
        return hobbiRemark;
    }

    public void setHobbiRemark(String hobbiRemark) {
        this.hobbiRemark = hobbiRemark;
    }

    public HobbiesBean(String hobbiName, String hobbiRemark) {

        this.hobbiName = hobbiName;
        this.hobbiRemark = hobbiRemark;

    }
}
