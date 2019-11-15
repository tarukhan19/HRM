package com.hrm.hrm.Bean;

/**
 * Created by shree on 3/13/2018.
 */

public class DetailCalenderBean {

    String datetv;
    String leaveType;
    String inOutTime;

    public String getdatetv() {
        return datetv;
    }

    public void setdatetv(String datetv) {
        this.datetv = datetv;
    }

    public String getleaveType() {
        return leaveType;
    }

    public void setleaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getinOutTime() {
        return inOutTime;
    }

    public void setinOutTime(String inOutTime) {
        this.inOutTime = inOutTime;
    }

    public DetailCalenderBean(String datetv, String leaveType, String inOutTime) {

        this.datetv = datetv;
        this.leaveType = leaveType;
        this.inOutTime = inOutTime;
    }
}
