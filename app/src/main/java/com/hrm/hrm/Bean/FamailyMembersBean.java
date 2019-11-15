package com.hrm.hrm.Bean;

/**
 * Created by Ishan Puranik on 15/11/2017.
 */

public class FamailyMembersBean {
    String nameFamily;
    String relation;
    String dateOfBirth;
    String addressFamily;
    String phoneNoFamily;
    String emailFamily;
    String gartutiyNominee;
    String pfNominee;
    String pensionNominee;
    String medicalInsurance;
    String medicalInsNominee;

    public String getNameFamily() {
        return nameFamily;
    }

    public void setNameFamily(String nameFamily) {
        this.nameFamily = nameFamily;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddressFamily() {
        return addressFamily;
    }

    public void setAddressFamily(String addressFamily) {
        this.addressFamily = addressFamily;
    }

    public String getPhoneNoFamily() {
        return phoneNoFamily;
    }

    public void setPhoneNoFamily(String phoneNoFamily) {
        this.phoneNoFamily = phoneNoFamily;
    }

    public String getEmailFamily() {
        return emailFamily;
    }

    public void setEmailFamily(String emailFamily) {
        this.emailFamily = emailFamily;
    }

    public String getGartutiyNominee() {
        return gartutiyNominee;
    }

    public void setGartutiyNominee(String gartutiyNominee) {
        this.gartutiyNominee = gartutiyNominee;
    }

    public String getPfNominee() {
        return pfNominee;
    }

    public void setPfNominee(String pfNominee) {
        this.pfNominee = pfNominee;
    }

    public String getPensionNominee() {
        return pensionNominee;
    }

    public void setPensionNominee(String pensionNominee) {
        this.pensionNominee = pensionNominee;
    }

    public String getMedicalInsurance() {
        return medicalInsurance;
    }

    public void setMedicalInsurance(String medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }

    public String getMedicalInsNominee() {
        return medicalInsNominee;
    }

    public void setMedicalInsNominee(String medicalInsNominee) {
        this.medicalInsNominee = medicalInsNominee;
    }

    public FamailyMembersBean(String nameFamily, String relation, String dateOfBirth, String addressFamily, String phoneNoFamily, String emailFamily, String gartutiyNominee, String pfNominee, String pensionNominee, String medicalInsurance, String medicalInsNominee) {

        this.nameFamily = nameFamily;
        this.relation = relation;
        this.dateOfBirth = dateOfBirth;
        this.addressFamily = addressFamily;
        this.phoneNoFamily = phoneNoFamily;
        this.emailFamily = emailFamily;
        this.gartutiyNominee = gartutiyNominee;
        this.pfNominee = pfNominee;
        this.pensionNominee = pensionNominee;
        this.medicalInsurance = medicalInsurance;
        this.medicalInsNominee = medicalInsNominee;
    }
}
