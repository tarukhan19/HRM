package com.hrm.hrm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hrm.hrm.Bean.DailyAttendence;
import com.hrm.hrm.utils.AppPreferences;
import com.hrm.hrm.utils.Endpoints;
import com.hrm.hrm.utils.MyDCalender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class CalenderView extends Activity
{

    MyDCalender myCalendar;
    TextView tv_date;
    LinearLayout ll_back, ll_history;
    ProgressDialog pd;
    ArrayList<DailyAttendence> dailyAttendences;
    ArrayList<String> attendence;
    String Cid;
    Context context;
    String time_in, time_out, working_status, no_of_late_come, atten_date,total_working_hrs;
    int dateLength;
    Spinner month;
    ArrayList<String> start_dateList,end_dateList,monthList,month_descList,tot_daysList;
    ArrayAdapter<String> start_dateAdapter,end_dateAdapter,monthAdapter,month_descAdapter,tot_daysAdapter;
    String start_dateS,end_dateS,monthS,month_descS,tot_daysS;
    Dialog dialog;

    JSONObject DailyHourObject;
    TextView tv_present, tv_absent, tv_half_day, tv_week_off, tv_late_mark,
            tv_paid_holiday, tv_casual_leave, tv_comp_off, tv_on_duty, tv_privilege, tv_Sick_Leave;
    Button show, viewInDetails;
    CalenderDtailAdapter calenderDtailAdapter;
    int pos;
    String EmployeeId, CompanyId, Present, Absent, HalfDay, WeekOff, PaidHolidays, LeaveType, CurrentBalance, AlreadyApplied, AvailableBalance, ApplyingFor, ClosingBalance;
    RequestQueue sr1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_view);

        start_dateList=new ArrayList<>();
        end_dateList=new ArrayList<>();
        monthList=new ArrayList<>();
        month_descList=new ArrayList<>();
        tot_daysList=new ArrayList<>();

        start_dateAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, start_dateList);
      //  start_dateAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        end_dateAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, end_dateList);
       // end_dateAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        monthAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, monthList);
       // monthAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        month_descAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, month_descList);
        month_descAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        tot_daysAdapter= new ArrayAdapter<String>(this, R.layout.spinner_item, tot_daysList);
       // tot_daysAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#A5C1EB"));
        }

        context = CalenderView.this;
        dailyAttendences = new ArrayList<>();
        attendence = new ArrayList<>();

        Cid = AppPreferences.loadPreferences(CalenderView.this, "CompanyId");

        tv_present = (TextView) findViewById(R.id.tv_present);
        tv_absent = (TextView) findViewById(R.id.tv_absent);
        tv_half_day = (TextView) findViewById(R.id.tv_half_day);
        tv_week_off = (TextView) findViewById(R.id.tv_week_off);
        tv_late_mark = (TextView) findViewById(R.id.tv_late_mark);
        tv_paid_holiday = (TextView) findViewById(R.id.tv_paid_holiday);
        tv_casual_leave = (TextView) findViewById(R.id.tv_casual_leave);
        tv_comp_off = (TextView) findViewById(R.id.tv_comp_off);
        tv_on_duty = (TextView) findViewById(R.id.tv_on_duty);
        tv_privilege = (TextView) findViewById(R.id.tv_privilege);
        tv_Sick_Leave = (TextView) findViewById(R.id.tv_Sick_Leave);
        month=(Spinner)findViewById(R.id.month) ;
        ll_back=findViewById(R.id.ll_back);
        show=(Button)findViewById(R.id.show);
        viewInDetails=(Button)findViewById(R.id.detail);

        ll_history = (LinearLayout) findViewById(R.id.ll_history);

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CalenderView.this,DashBord.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

            }
        });

        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month_descS = month.getItemAtPosition(month.getSelectedItemPosition()).toString();
                //Log.e("month_descS",month_descS);
                start_dateS = start_dateList.get(position).toString();
               // Log.e("start_dateS",start_dateS);
                end_dateS = end_dateList.get(position).toString();
                //Log.e("end_dateS",end_dateS);
                monthS = monthList.get(position).toString();
                //Log.e("monthS",monthS);
                tot_daysS = tot_daysList.get(position).toString();
               // Log.e("tot_daysS",tot_daysS);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerData();
        show.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
//                ll_history.setVisibility(View.VISIBLE);
//                viewInDetails.setVisibility(View.VISIBLE);


                dailyAttendence(monthS,start_dateS,end_dateS);



            }
        });

        viewInDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateInDetails();

            }
        });
    }

    private void dateInDetails()
    {
        dialog = new Dialog(CalenderView.this,R.style.full_screen_dialog);
        dialog.setContentView(R.layout.dialogdetailcalender);
        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        int numberOfColumns = 3;
        // set up the RecyclerView
       RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.rvNumber);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        calenderDtailAdapter = new CalenderDtailAdapter(CalenderView.this,dailyAttendences);
        recyclerView.setAdapter(calenderDtailAdapter);
        dialog.show();
    }

    private void spinnerData()
    {
        pd = new ProgressDialog(context);
        pd.setMessage("Loading Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        String url = Endpoints.GETTOPMONTH;
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(CalenderView.this, "CompanyCode"));
        jsonParams.put("CompanyId",AppPreferences.loadPreferences(CalenderView.this, "CompanyId"));
        Log.e("GETTOPMONTH",jsonParams.toString());

        @SuppressLint({"NewApi", "LocalSuppress"}) RequestQueue requestQueue=Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    Log.e("GETTOPMONTH",response.toString());
                try {
                    pd.dismiss();
                    Log.e("spinnerDataresponse",response+" ");
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    boolean status = obj.getBoolean("result");
                    String Messages=obj.getString("Messages");
                    //   Toast.makeText(getApplicationContext(),"4",Toast.LENGTH_LONG).show();
                    if(status==true && Messages.equalsIgnoreCase("Successfull"))
                    {
                        pd.dismiss();
                        month_descList.clear();
                        monthList.clear();
                        start_dateList.clear();
                        end_dateList.clear();
                        tot_daysList.clear();

                        final JSONArray jsonarray=obj.getJSONArray("data");
                        for(int i=0;i<jsonarray.length();i++)
                        {
                           JSONObject jsonObject1=jsonarray.getJSONObject(i);
                           String mon=jsonObject1.getString("month");
                           String mon_desc=jsonObject1.getString("month_desc");
                           String st_date=jsonObject1.getString("start_date");
                           String en_date=jsonObject1.getString("end_date");
                           String t_day=jsonObject1.getString("tot_days");
                            month_descList.add(mon_desc);
                            monthList.add(mon);
                            start_dateList.add(st_date);
                            end_dateList.add(en_date);
                            tot_daysList.add(t_day);
                            month_descAdapter.notifyDataSetChanged();

                        }
                    }
                    month.setAdapter(month_descAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 Toast.makeText(CalenderView.this,"error",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        requestQueue.add(request);


    }


    public void dailyAttendence(String monthcount,String fromDate,String toDate)
    {

        pd = new ProgressDialog(context);
        pd.setMessage("Loading Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        sr1 = Volley.newRequestQueue(this);
      //  String url = "http://103.205.65.52/MobileApp/GetEmployeeDailyAttendance";
        //String url = "http://103.205.65.52/MobileAppTest/GetEmployeeDailyAttendance";
        String url = Endpoints.DAILY_ATTENDANCE;

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(CalenderView.this, "CompanyCode"));
        jsonParams.put("EmployeeId", AppPreferences.loadPreferences(CalenderView.this, "EmployeeId"));
        jsonParams.put("CompanyId",AppPreferences.loadPreferences(CalenderView.this, "CompanyId"));
        jsonParams.put("MonthID", monthcount);
        jsonParams.put("FromDate", fromDate);
        jsonParams.put("ToDate", toDate);
        jsonParams.put("Action","Leave");

        Log.e("jsonParams",jsonParams+" " );




        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("====DailyAttendence===" ,">>>>"+ response);
                        pd.dismiss();
                        try {
                            ll_history.setVisibility(View.VISIBLE);
                            viewInDetails.setVisibility(View.VISIBLE);
                            pd.dismiss();

                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            boolean result = jsonObject.getBoolean("result");
                            String Messages = jsonObject.getString("Messages");

                            if (result==true && Messages.equalsIgnoreCase("Successfull")) {
                                dailyAttendences.clear();
                                attendence.clear();

                                JSONObject data = jsonObject.getJSONObject("data");
                                EmployeeId = data.getString("EmployeeId");
                                CompanyId = data.getString("CompanyId");
                                Present = data.getString("Present");
                                Absent = data.getString("Absent");
                                HalfDay = data.getString("HalfDay");
                                WeekOff = data.getString("WeekOff");
                                PaidHolidays = data.getString("PaidHolidays");

                                tv_present.setText(Present);
                                tv_absent.setText(Absent);
                                tv_half_day.setText(HalfDay);
                                tv_week_off.setText(WeekOff);
                                tv_paid_holiday.setText(PaidHolidays);


//                                System.out.println("====PaidHolidays===" + PaidHolidays);
                                JSONObject OtherLeave = data.getJSONObject("OtherLeave");
                                LeaveType = OtherLeave.getString("LeaveType");
                                CurrentBalance = OtherLeave.getString("CurrentBalance");
                                AlreadyApplied = OtherLeave.getString("AlreadyApplied");
                                AvailableBalance = OtherLeave.getString("AvailableBalance");
                                ApplyingFor = OtherLeave.getString("ApplyingFor");
                                ClosingBalance = OtherLeave.getString("ClosingBalance");


                                JSONArray DailyHours = data.getJSONArray("WorkingStatusList");
                                for (int i = 0; i < DailyHours.length(); i++) {
                                    dateLength=DailyHours.length();
                                    Log.e("dateLength",">>"+dateLength);

                                    JSONObject dataobj = DailyHours.getJSONObject(i);
                                    DailyHourObject=dataobj;
                                    DailyAttendence dA=new DailyAttendence(time_in,time_out,working_status,no_of_late_come,atten_date,total_working_hrs);

                                    time_in=dataobj.getString("Log_In");
                                    time_out=dataobj.getString("Log_Out");
                                    working_status=dataobj.getString("workingStatus");
                                    no_of_late_come=dataobj.getString("NoOfLateCome");
                                    atten_date=dataobj.getString("Date");
                                    total_working_hrs=dataobj.getString("TotalworkHour");

                                    dA.setatten_date(atten_date);
                                    dA.setworking_status(working_status);
                                    dA.settimein(time_in);
                                    dA.settime_out(time_out);






                                    attendence.add(no_of_late_come + "," + time_in + "," + time_out + "," + working_status+ "," + atten_date+ "," + total_working_hrs);

                                    System.out.println("====DailyHours===" + attendence.toString());
                                    dailyAttendences.add(new DailyAttendence(no_of_late_come, time_in, time_out, working_status,atten_date,total_working_hrs));
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        if (null != error.networkResponse) {
                            System.out.println("====response===" + error);
                        }
                    }
                });
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        sr1.add(request);
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.trans_left_in,
                R.anim.trans_left_out);
    }
}
