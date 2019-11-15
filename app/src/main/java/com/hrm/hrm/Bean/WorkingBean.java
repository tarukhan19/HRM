package com.hrm.hrm.Bean;

/**
 * Created by Ishan Puranik on 15/11/2017.
 */

public class WorkingBean {
    String employerDetail;
    String place;
    String fromDate;
    String toDate;
    String basicSalary;
    String totalSalary;
    String reasonForLeaving;
    String desigScope;
    String supervisiorDetail;
    String currency;
    String totalExperience;


    public String getEmployerDetail() {
        return employerDetail;
    }

    public void setEmployerDetail(String employerDetail) {
        this.employerDetail = employerDetail;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(String basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(String totalSalary) {
        this.totalSalary = totalSalary;
    }

    public String getReasonForLeaving() {
        return reasonForLeaving;
    }

    public void setReasonForLeaving(String reasonForLeaving) {
        this.reasonForLeaving = reasonForLeaving;
    }

    public String getDesigScope() {
        return desigScope;
    }

    public void setDesigScope(String desigScope) {
        this.desigScope = desigScope;
    }

    public String getSupervisiorDetail() {
        return supervisiorDetail;
    }

    public void setSupervisiorDetail(String supervisiorDetail) {
        this.supervisiorDetail = supervisiorDetail;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(String totalExperience) {
        this.totalExperience = totalExperience;
    }

    public WorkingBean(String employerDetail, String place, String fromDate, String toDate, String basicSalary, String totalSalary, String reasonForLeaving, String desigScope, String supervisiorDetail, String currency, String totalExperience) {
        this.employerDetail = employerDetail;
        this.place = place;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.basicSalary = basicSalary;
        this.totalSalary = totalSalary;
        this.reasonForLeaving = reasonForLeaving;
        this.desigScope = desigScope;
        this.supervisiorDetail = supervisiorDetail;
        this.currency = currency;
        this.totalExperience = totalExperience;

    }
}
