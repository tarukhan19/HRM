package com.hrm.hrm.Bean;

/**
 * Created by Ishan Puranik on 15/11/2017.
 */

public class QualificationBean {

    String qualificationName;
    String instituteName;
    String boardName;
    String mainSubjectName;
    String divisionName;
    String marksPercentage;
    String passingYear;
    String weightage;

    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getMainSubjectName() {
        return mainSubjectName;
    }

    public void setMainSubjectName(String mainSubjectName) {
        this.mainSubjectName = mainSubjectName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getMarksPercentage() {
        return marksPercentage;
    }

    public void setMarksPercentage(String marksPercentage) {
        this.marksPercentage = marksPercentage;
    }

    public String getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(String passingYear) {
        this.passingYear = passingYear;
    }

    public String getWeightage() {
        return weightage;
    }

    public void setWeightage(String weightage) {
        this.weightage = weightage;
    }

    public QualificationBean(String qualificationName, String instituteName, String boardName, String mainSubjectName, String divisionName, String marksPercentage, String passingYear, String weightage) {
        this.qualificationName = qualificationName;
        this.instituteName = instituteName;
        this.boardName = boardName;
        this.mainSubjectName = mainSubjectName;
        this.divisionName = divisionName;
        this.marksPercentage = marksPercentage;
        this.passingYear = passingYear;
        this.weightage = weightage;
    }
}
