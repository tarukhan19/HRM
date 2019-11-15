package com.hrm.hrm.Bean;

/**
 * Created by Ishan Puranik on 16/11/2017.
 */

public class HolidayBean {
    String hId;
    String hDate;
    String holidayDesc;

    public String gethId() {
        return hId;
    }

    public void sethId(String hId) {
        this.hId = hId;
    }

    public String gethDate() {
        return hDate;
    }

    public void sethDate(String hDate) {
        this.hDate = hDate;
    }

    public String getHolidayDesc() {
        return holidayDesc;
    }

    public void setHolidayDesc(String holidayDesc) {
        this.holidayDesc = holidayDesc;
    }

    public HolidayBean(String hId, String hDate, String holidayDesc) {

        this.hId = hId;
        this.hDate = hDate;
        this.holidayDesc = holidayDesc;
    }
}
