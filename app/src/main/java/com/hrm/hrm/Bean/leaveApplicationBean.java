package com.hrm.hrm.Bean;

/**
 * Created by Ishan Puranik on 10/12/2017.
 */

public class leaveApplicationBean {
    String SNo;
    String companyId;
    String employeeId;
    String employeeName;
    String approver;
    String leaveType;
    String fromDate;
    String leaveAppDate;
    String firstHalf;
    String toDate;
    String secondHalf;
    String timeFrom;
    String timeTo;
    String days;
    String remarks;
    String statusnew;
    String leaveAppCount;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getLeaveAppDate() {
        return leaveAppDate;
    }

    public void setLeaveAppDate(String leaveAppDate) {
        this.leaveAppDate = leaveAppDate;
    }

    public String getFirstHalf() {
        return firstHalf;
    }

    public void setFirstHalf(String firstHalf) {
        this.firstHalf = firstHalf;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getSecondHalf() {
        return secondHalf;
    }

    public void setSecondHalf(String secondHalf) {
        this.secondHalf = secondHalf;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatusnew() {
        return statusnew;
    }

    public void setStatusnew(String statusnew) {
        this.statusnew = statusnew;
    }

    public String getLeaveAppCount() {
        return leaveAppCount;
    }

    public void setLeaveAppCount(String leaveAppCount) {
        this.leaveAppCount = leaveAppCount;
    }

    public String getSNo() {
        return SNo;
    }

    public void setSNo(String SNo) {
        this.SNo = SNo;
    }

    public leaveApplicationBean(String SNo, String companyId, String employeeId, String employeeName,
                                String approver, String leaveType, String fromDate,
                                String leaveAppDate, String firstHalf, String toDate,
                                String secondHalf,
                                String timeFrom, String timeTo, String days, String remarks, String statusnew, String leaveAppCount) {


        this.SNo = SNo;
        this.companyId = companyId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.approver = approver;
        this.leaveType = leaveType;
        this.fromDate = fromDate;
        this.leaveAppDate = leaveAppDate;
        this.firstHalf = firstHalf;
        this.toDate = toDate;
        this.secondHalf = secondHalf;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.days = days;
        this.remarks = remarks;
        this.statusnew = statusnew;
        this.leaveAppCount = leaveAppCount;


    }
}
