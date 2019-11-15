package com.hrm.hrm.Bean;

/**
 * Created by Ishan Puranik on 16/11/2017.
 */

public class BirthdayBean {

    String employeeId;
    String employeeCode;
    String name;
    String BirthDate;
    String DaysToGo;


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getDaysToGo() {
        return DaysToGo;
    }

    public void setDaysToGo(String daysToGo) {
        DaysToGo = daysToGo;
    }

    public BirthdayBean(String employeeId, String employeeCode, String name,
                        String BirthDate, String DaysToGo) {

        this.employeeId = employeeId;
        this.employeeCode = employeeCode;
        this.name = name;
        this.BirthDate = BirthDate;
        this.DaysToGo = DaysToGo;
    }
}
