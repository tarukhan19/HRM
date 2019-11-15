package com.hrm.hrm.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ishan Puranik on 12/12/2017.
 */

public class DailyAttendence {

    String no_of_late_come;
    String time_in;
    String time_out;
    String working_status;
    String atten_date;
    String total_working_hrs;





    public String getnooflatecome() {
        return no_of_late_come;
    }

    public void setnooflatecome(String no_of_late_come) {
        this.no_of_late_come = no_of_late_come;
    }


    public String gettimein() {
        return time_in;
    }

    public void settimein(String time_in) {
        this.time_in = time_in;
    }


    public String gettime_out() {
        return time_out;
    }

    public void settime_out(String time_out) {
        this.time_out = time_out;
    }


    public String getworking_status() {
        return working_status;
    }

    public void setworking_status(String working_status) {
        this.working_status = working_status;
    }


    public String getatten_date() {
        return atten_date;
    }

    public void setatten_date(String atten_date) {
        this.atten_date =atten_date ;
    }


    public String gettotal_working_hrs() {
        return total_working_hrs;
    }

    public void settotal_working_hrs(String total_working_hrs) {
        this.total_working_hrs = total_working_hrs;
    }

    public DailyAttendence(String no_of_late_come,String time_in,String time_out,String working_status,String atten_date,String total_working_hrs) {

        this.no_of_late_come = no_of_late_come;
        this.time_in = time_in;
        this.time_out = time_out;
        this.working_status = working_status;
        this.atten_date=atten_date;
        this.total_working_hrs=total_working_hrs;

    }


}


//public class DailyAttendence implements Parcelable {
//
//    String no_of_late_come;
//    String time_in;
//    String time_out;
//    String working_status;
//    String atten_date;
//    String total_working_hrs;
//
//
//    protected DailyAttendence(Parcel in) {
//        no_of_late_come = in.readString();
//        time_in = in.readString();
//        time_out = in.readString();
//        working_status = in.readString();
//        atten_date = in.readString();
//        total_working_hrs = in.readString();
//    }
//
//    public static final Creator<DailyAttendence> CREATOR = new Creator<DailyAttendence>() {
//        @Override
//        public DailyAttendence createFromParcel(Parcel in) {
//            return new DailyAttendence(in);
//        }
//
//        @Override
//        public DailyAttendence[] newArray(int size) {
//            return new DailyAttendence[size];
//        }
//    };
//
//    public String getnooflatecome() {
//        return no_of_late_come;
//    }
//
//    public void setnooflatecome(String no_of_late_come) {
//        this.no_of_late_come = no_of_late_come;
//    }
//
//
//    public String gettimein() {
//        return time_in;
//    }
//
//    public void settimein(String time_in) {
//        this.time_in = time_in;
//    }
//
//
//    public String gettime_out() {
//        return time_out;
//    }
//
//    public void settime_out(String time_out) {
//        this.time_out = time_out;
//    }
//
//
//    public String getworking_status() {
//        return working_status;
//    }
//
//    public void setworking_status(String working_status) {
//        this.working_status = working_status;
//    }
//
//
//    public String getatten_date() {
//        return atten_date;
//    }
//
//    public void setatten_date(String atten_date) {
//        this.atten_date =atten_date ;
//    }
//
//
//    public String gettotal_working_hrs() {
//        return total_working_hrs;
//    }
//
//    public void settotal_working_hrs(String total_working_hrs) {
//        this.total_working_hrs = total_working_hrs;
//    }
//
//    public DailyAttendence(String no_of_late_come,String time_in,String time_out,String working_status,String atten_date,String total_working_hrs) {
//
//        this.no_of_late_come = no_of_late_come;
//        this.time_in = time_in;
//        this.time_out = time_out;
//        this.working_status = working_status;
//        this.atten_date=atten_date;
//        this.total_working_hrs=total_working_hrs;
//
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(no_of_late_come);
//        parcel.writeString(time_in);
//        parcel.writeString(time_out);
//        parcel.writeString(working_status);
//        parcel.writeString(atten_date);
//        parcel.writeString(total_working_hrs);
//    }
//}
