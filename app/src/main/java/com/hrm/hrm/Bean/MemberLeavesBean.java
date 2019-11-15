package com.hrm.hrm.Bean;

/**
 * Created by Ishan Puranik on 17/12/2017.
 */

public class MemberLeavesBean {

    private String leave;
    private String pl;
    private String cl;
    private String sl;
    private String od;
    private String co;
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public String getPl() {
        return pl;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }

    public String getCl() {
        return cl;
    }

    public void setCl(String cl) {
        this.cl = cl;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getOd() {
        return od;
    }

    public void setOd(String od) {
        this.od = od;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public MemberLeavesBean(String leave, String pl, String cl, String sl, String od, String co,boolean status) {
        this.leave = leave;
        this.pl = pl;
        this.cl = cl;
        this.sl = sl;
        this.od = od;
        this.co = co;
        this.status=status;
    }
}