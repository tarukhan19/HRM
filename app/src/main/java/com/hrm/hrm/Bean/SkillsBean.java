package com.hrm.hrm.Bean;

/**
 * Created by Ishan Puranik on 15/11/2017.
 */

public class SkillsBean {
    String skillName;
    String skillRemark;

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillRemark() {
        return skillRemark;
    }

    public void setSkillRemark(String skillRemark) {
        this.skillRemark = skillRemark;
    }

    public SkillsBean(String skillName, String skillRemark) {
        this.skillName = skillName;
        this.skillRemark = skillRemark;
    }
}
