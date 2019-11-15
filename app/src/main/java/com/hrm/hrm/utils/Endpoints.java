package com.hrm.hrm.utils;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class Endpoints
{
//    public static final String BASE_URL = "http://103.205.65.52/MobileApp/";

   // public static final String BASE_URL_TEST = "http://103.205.65.52/MobileAppTest/";

    public static String BASE_URL;
    public static  String niplUrl="http://103.205.65.52/MobileApp/";
    public static  String kseUrl="http://182.74.167.84/MobileApp/";
    public static  String LOGIN;
    public static  String GETMYPROFILE;
    public static  String DASHBOARDATTRIBUTECOUNT;
    public static  String DASHBOARDATTRIBUTE;
    public static  String GETTOPMONTH;
    public static  String DAILY_ATTENDANCE;
    public static  String GETLEAVEAPPFORAPPROVE;
    public static  String GETLEAVEAPPCOUNT;
    public static  String DELETELEAVEAPP;
    public static  String FORGOTPASSWORD;
    public static  String MYPROFILE;
    public static  String INSERTLEAVEAPP;
    public static  String LEAVEDETAILS;
    public static  String PAY_MONTHLIST;
    public static  String EMPLOYEEPAYSLIP;
    public static  String PUNCHATTENDANCE;
    public static  String LEAVEAPPAPPROVE;
    public static  String ACCEPTREJECT;
    public static  String LOCATIONREGISTER;
    public static  String LOADPUNCH;

    public Endpoints(String clientid)
    {

        Log.e("clientid",clientid);

            if (clientid.equalsIgnoreCase("kse"))
            {
                BASE_URL =kseUrl;
            }
            else
            {
                BASE_URL=niplUrl;

            }

            Log.e("baseurl",BASE_URL);

        LOGIN=BASE_URL+"EmployeeLogin";
        GETMYPROFILE=BASE_URL+ "GetMyProfile";
        DASHBOARDATTRIBUTECOUNT=BASE_URL+ "GetDashbordAttriCount";
        DASHBOARDATTRIBUTE=BASE_URL+ "GetDashbordAttriApi";
        GETTOPMONTH=BASE_URL+ "GetTopMonth";
        DAILY_ATTENDANCE=BASE_URL+ "GetEmployeeDailyAttendance";
        GETLEAVEAPPFORAPPROVE=BASE_URL+ "GetLeaveApplicationForApprove";
        GETLEAVEAPPCOUNT=BASE_URL+ "GetLeaveApplicationCount";
        DELETELEAVEAPP=BASE_URL+ "DeleteLeaveApplication";
        FORGOTPASSWORD=BASE_URL+ "GetForgotPassword";
        MYPROFILE=BASE_URL+ "GetMyProfile";
        INSERTLEAVEAPP=BASE_URL+ "InsertLeaveApplication";
        LEAVEDETAILS=BASE_URL+ "GetMemberLeavesDetails";
        PAY_MONTHLIST=BASE_URL+ "GetMonthList";
        EMPLOYEEPAYSLIP=BASE_URL+ "GetEmployeePaySlip";
        PUNCHATTENDANCE=BASE_URL+ "PunchAttendanceApi";
        LEAVEAPPAPPROVE=BASE_URL+ "GetLeaveApplicationForApprove";
        ACCEPTREJECT=BASE_URL+ "AcceptRejectLeaveApplication";
        LOCATIONREGISTER=BASE_URL+"RegisterAreaApi";
        LOADPUNCH=BASE_URL+"GetPunchDetails";

    }



    public String forPunchAttendance(String urlString, JSONObject params, byte[] profilepic)
            throws Exception
    {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        InputStream inputStream = null;

        String twoHyphens = "--";
        String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
        String lineEnd = "\r\n";

        String result = "";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());

            if (profilepic != null) {
                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"Photo\"; filename=\"profilepic.png\"" + lineEnd);
                //outputStream.writeBytes("Content-Type: " + fileMimeType + lineEnd);
                outputStream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);

                outputStream.writeBytes(lineEnd);

                for (byte b : profilepic) {
                    outputStream.write(b);
                }

                outputStream.writeBytes(lineEnd);
            }

            // Upload POST Data
            Iterator<String> keys = params.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = params.get(key).toString();

                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
                outputStream.writeBytes("Content-Type: text/plain" + lineEnd);
                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(value);
                outputStream.writeBytes(lineEnd);
            }

            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            inputStream = connection.getInputStream();

            result = this.convertStreamToString(inputStream);
            inputStream.close();
            outputStream.flush();
            outputStream.close();

            return result;
        } catch (Exception e) {
            Log.e("REg Error ", e.getMessage());
        }
        return result;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
