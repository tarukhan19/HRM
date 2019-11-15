package com.hrm.hrm;

import android.app.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.hrm.hrm.Bean.MemberLeavesBean;
import com.hrm.hrm.utils.AppPreferences;
import com.hrm.hrm.utils.Endpoints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class NewApplication extends Activity {

    LinearLayout ll_back;
    ProgressDialog pd;
    TextView edit_date, tv_from_date, tv_from_date1, edit_days, edit_for_date, edit_emp_code, edit_emp_name, edit_approver;
    EditText edit_remark;
    Spinner spinner_leave, sp_From, spinner_to, sp_From_time1, sp_From_time2, sp_to_time2;
    TextView sp_to_time1;
    LinearLayout ll_date_from, ll_time_from;
    String leavetext = "", startDate1 = "", startDate = "", endDate1 = "", endDate = "", forDate1 = "", forDate = "";
    ArrayList<MemberLeavesBean> memberList;
    MemberLeavesAdapter memberLeavesAdapter;
    MemberCOAdapter memberCOAdapter;
    MemberPLAdapter memberPLAdapter;
    MemberSLAdapter memberSLAdapter;
    MemberODAdapter memberODAdapter;
    MemberLTAdapter memberLTAdapter;
    String sp_From_status, sp_to_status, leavebalance = "0.0", copmpareleaves;
    int position, lastposition;

    String EmployeeId, CompanyId, CompanyCode, Approver, ApproverId, LeaveType, FromDate, FirstHalf, ToDate, TimeFrom, TimeTo,
            LeaveAppDate, SecondHalf, Days, Remarks, status;
    Date date1, date2;
    long diff;
    String leavet, currentb, alreadya, availableb, applyingf, closingb;
    int newSpinerPos, newSpinerPos2, newSpinerPos3;
    double total;
    double total1;
    double sp_From_status1, sp_To_status1;
    String sp_From_status_text1, sp_To_status_text1;
    LinearLayout ll_submit, ll_cancel;
    float subtotal1;
    private OkHttpClient clientHttp;
    private RequestBody requestBody;
    int fromStatus1, fromStatus2, toStaus1, toStatus2;
    String formattedDate;
    String sp_From_time_status1, sp_From_time_status2, sp_to_time_status1, sp_to_time_status2;
    double af1, af2, af3, af4 = 0.0;
    ExpandableHeightListView gv_AA, gv_CTB, gv_AB, gv_CB, gv_AF, gv_LT;
    ArrayList<String> personNames;
    int short_leave_time = 0;
    ArrayList<String> leavearraylist, closingBalanceList, applyngforlist, fromtimelist;
    ArrayAdapter<String> leavearrayadapter, fromtimeadapter;
    String closignBal, applyingfor, cbS = "0", afS = "0";
    HashSet<MemberLeavesBean> hashSet;
    LinearLayout fromLL;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_application);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#A5C1EB"));
        }
        String Cid = AppPreferences.loadPreferences(NewApplication.this, "CompanyId");
        String Cc = AppPreferences.loadPreferences(NewApplication.this, "CompanyCode");

        hashSet = new HashSet<MemberLeavesBean>();
        memberList = new ArrayList<>();
        personNames = new ArrayList<>();
        leavearraylist = new ArrayList<>();
        closingBalanceList = new ArrayList<>();
        applyngforlist = new ArrayList<>();
        fromtimelist = new ArrayList<>();
        leavearrayadapter = new ArrayAdapter<String>(this, R.layout.spinner_item, leavearraylist);
        leavearrayadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        fromLL=findViewById(R.id.fromLL);
        fromtimeadapter = new ArrayAdapter<String>(this, R.layout.spinner_item, fromtimelist);
        fromtimeadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        gv_AA = (ExpandableHeightListView) findViewById(R.id.gv_AA);
        gv_CTB = (ExpandableHeightListView) findViewById(R.id.gv_CTB);
        gv_AB = (ExpandableHeightListView) findViewById(R.id.gv_AB);
        gv_CB = (ExpandableHeightListView) findViewById(R.id.gv_CB);
        gv_AF = (ExpandableHeightListView) findViewById(R.id.gv_AF);
        gv_LT = (ExpandableHeightListView) findViewById(R.id.gv_LT);

        GetMemberLeavesDetails();

        edit_emp_code = (TextView) findViewById(R.id.edit_emp_code);
        edit_emp_name = (TextView) findViewById(R.id.edit_emp_name);
        edit_date = (TextView) findViewById(R.id.edit_date);
        edit_approver = (TextView) findViewById(R.id.edit_approver);
        edit_for_date = (TextView) findViewById(R.id.edit_for_date);
        tv_from_date = (TextView) findViewById(R.id.tv_from_date);
        tv_from_date1 = (TextView) findViewById(R.id.tv_from_date1);
        edit_days = (TextView) findViewById(R.id.edit_days);
        edit_remark = (EditText) findViewById(R.id.edit_remark);

        clientHttp = new OkHttpClient();

        edit_emp_code.setText(AppPreferences.loadPreferences(NewApplication.this, "EmpCode"));
        edit_emp_code.setEnabled(true);
        edit_emp_name.setText(AppPreferences.loadPreferences(NewApplication.this, "Name"));
        edit_emp_code.setEnabled(true);

        String approvar = AppPreferences.loadPreferences(NewApplication.this, "Approver");
        if (approvar.equalsIgnoreCase("null")) {
            edit_approver.setText("");
        } else {
            edit_approver.setText(AppPreferences.loadPreferences(NewApplication.this, "Approver"));
        }
        edit_approver.setEnabled(true);

        ll_date_from = (LinearLayout) findViewById(R.id.ll_date_from);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_submit = (LinearLayout) findViewById(R.id.ll_submit);
        ll_cancel = (LinearLayout) findViewById(R.id.ll_cancel);

        spinner_leave = (Spinner) findViewById(R.id.spinner_leave);
        sp_From = (Spinner) findViewById(R.id.sp_From);
        spinner_to = (Spinner) findViewById(R.id.spinner_to);
        sp_From_time1 = (Spinner) findViewById(R.id.sp_From_time1);
        sp_From_time2 = (Spinner) findViewById(R.id.sp_From_time2);
        sp_to_time1 = (TextView) findViewById(R.id.sp_to_time1);
        sp_to_time2 = (Spinner) findViewById(R.id.sp_to_time2);
        edit_days.setText("0");


        sp_From_time1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                sp_From_time_status1 = sp_From_time1.getSelectedItem().toString();
                newSpinerPos2 = sp_From_time1.getSelectedItemPosition();
                String val = sp_From_time1.getSelectedItem().toString();
                int value=Integer.parseInt(val);
                sp_to_time1.setText(String.valueOf(value + short_leave_time));


                ((TextView) adapterView.getChildAt(0)).setTextSize(16);
                Typeface bahamas = Typeface.createFromAsset(getAssets(), "font/raleway.regular.ttf");

                ((TextView) adapterView.getChildAt(0)).setTypeface(bahamas);
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#2A5F3D"));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fromLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_From_time1.performClick();
            }
        });

        sp_From_time2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                newSpinerPos3 = sp_From_time2.getSelectedItemPosition();
                sp_From_time_status2 = sp_From_time2.getSelectedItem().toString();
                ((TextView) adapterView.getChildAt(0)).setTextSize(16);
                Typeface bahamas = Typeface.createFromAsset(getAssets(), "font/raleway.regular.ttf");

                ((TextView) adapterView.getChildAt(0)).setTypeface(bahamas);
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#2A5F3D"));
                sp_to_time2.setSelection(i);
                sp_to_time2.setEnabled(false);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_to_time2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sp_to_time_status2 = sp_to_time2.getSelectedItem().toString();
                ((TextView) adapterView.getChildAt(0)).setTextSize(16);
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#2A5F3D"));
                Typeface bahamas = Typeface.createFromAsset(getAssets(), "font/raleway.regular.ttf");

                ((TextView) adapterView.getChildAt(0)).setTypeface(bahamas);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_From.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                sp_From_status = sp_From.getSelectedItem().toString();
                sp_to_status = spinner_to.getSelectedItem().toString();
                ((TextView) adapterView.getChildAt(0)).setTextSize(16);
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#2A5F3D"));
                Typeface bahamas = Typeface.createFromAsset(getAssets(), "font/raleway.regular.ttf");

                ((TextView) adapterView.getChildAt(0)).setTypeface(bahamas);
                calculateDuration();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sp_From_status = sp_From.getSelectedItem().toString();
                sp_to_status = spinner_to.getSelectedItem().toString();
                ((TextView) adapterView.getChildAt(0)).setTextSize(16);
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#2A5F3D"));
                Typeface bahamas = Typeface.createFromAsset(getAssets(), "font/raleway.regular.ttf");
                ((TextView) adapterView.getChildAt(0)).setTypeface(bahamas);
                calculateDuration();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ll_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EmployeeId = AppPreferences.loadPreferences(NewApplication.this, "EmployeeId");
                CompanyId = AppPreferences.loadPreferences(NewApplication.this, "CompanyId");
                Approver = AppPreferences.loadPreferences(NewApplication.this, "Approver");
                ApproverId = AppPreferences.loadPreferences(NewApplication.this, "Approverid");
                CompanyCode = AppPreferences.loadPreferences(NewApplication.this, "CompanyCode");
                if (ll_date_from.getVisibility() == View.VISIBLE) {
                    if (tv_from_date.getText().toString().equalsIgnoreCase(null)) {
                        Toast.makeText(NewApplication.this, "Please Select Start Date", Toast.LENGTH_SHORT).show();
                    } else if (tv_from_date.getText().toString().length() == 0) {
                        Toast.makeText(NewApplication.this, "Please Select Start Date", Toast.LENGTH_SHORT).show();
                    } else if (tv_from_date1.getText().toString().equalsIgnoreCase(null)) {
                        Toast.makeText(NewApplication.this, "Please Select To Date", Toast.LENGTH_SHORT).show();
                    } else if (tv_from_date1.getText().toString().length() == 0) {
                        Toast.makeText(NewApplication.this, "Please Select To Date", Toast.LENGTH_SHORT).show();

                    } else if (edit_remark.getText().toString().equalsIgnoreCase(null)) {
                        Toast.makeText(NewApplication.this, "Please Enter Remark", Toast.LENGTH_SHORT).show();

                    } else if (edit_remark.getText().toString().length() == 0) {
                        Toast.makeText(NewApplication.this, "Please Enter Remark", Toast.LENGTH_SHORT).show();

                    } else {

                        String dateee = edit_date.getText().toString();
                        Log.e("yyyy-MM-dd", dateee);
                        try {
                            Date datenew = new SimpleDateFormat("dd/MM/yyyy").parse(dateee);
                            formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(datenew);
                            Log.e("LeaveAppDate", formattedDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        LeaveType = leavetext.toString().trim();
                        FromDate = startDate1.toString().trim();
                        FirstHalf = sp_From_status.toString().trim();
                        ToDate = endDate1.toString().trim();
                        TimeFrom = "";
                        TimeTo = "";
                        LeaveAppDate = formattedDate;
                        SecondHalf = sp_to_status.toString().trim();
                        Days = edit_days.getText().toString().trim();
                        Remarks = edit_remark.getText().toString().trim();
                        status = "Submitted";
//                        SubmitLeave1();
                        SubmitLeave(EmployeeId, CompanyId, ApproverId, LeaveType, FromDate, FirstHalf, ToDate, TimeFrom, TimeTo, LeaveAppDate, SecondHalf, Days, Remarks, status);
                    }
                } else if (ll_time_from.getVisibility() == View.VISIBLE) {

                    if (edit_for_date.getText().toString().length() == 0) {
                        Toast.makeText(NewApplication.this, "Please Enter For Date", Toast.LENGTH_SHORT).show();

                    } else if (edit_for_date.getText().toString().equalsIgnoreCase(null)) {
                        Toast.makeText(NewApplication.this, "Please Enter For Date", Toast.LENGTH_SHORT).show();

                    } else if (edit_remark.getText().toString().length() == 0) {
                        Toast.makeText(NewApplication.this, "Please Enter Remark", Toast.LENGTH_SHORT).show();

                    } else if (edit_remark.getText().toString().equalsIgnoreCase(null)) {
                        Toast.makeText(NewApplication.this, "Please Enter Remark", Toast.LENGTH_SHORT).show();

                    } else {

                        String date = edit_for_date.getText().toString();
                        Log.e("yyyy-MM-dd", date);
                        try {
                            Date datenew = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                            formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(datenew);
                            Log.e("LeaveAppDate", formattedDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

//
//                        Log.e("yyyy-MM-dd", date);
//                        try {
//                            Date datenew = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//                            formattedDate = new SimpleDateFormat("dd MMM yyyy").format(datenew);
//                            Log.e("LeaveAppDate", formattedDate);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
                        LeaveType = leavetext.toString().trim();
                        FromDate = formattedDate;
                        FirstHalf = "From First Half";
                        ToDate = formattedDate;
                        TimeFrom = "";
                        TimeTo = "";
                        //LeaveAppDate = formattedDate;
                        LeaveAppDate = formattedDate;
                        SecondHalf = "To Second Half";
                        Days = "0";
                        Remarks = edit_remark.getText().toString().trim();
                        status = "Submitted";
//                        SubmitLeave1();
                        SubmitLeave(EmployeeId, CompanyId, ApproverId, LeaveType, FromDate, FirstHalf, ToDate, TimeFrom, TimeTo, LeaveAppDate, SecondHalf, Days, Remarks, status);
                    }
                }
            }
        });
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (date2 != null) {
                    tv_from_date1.setText(" ");
                    edit_days.setText("");

                    if (afS != null && cbS != null) {

                        memberList.get(lastposition).setOd(String.valueOf(afS));
                        memberList.get(lastposition).setSl(String.valueOf(cbS));
                        memberSLAdapter.notifyDataSetChanged();
                        memberODAdapter.notifyDataSetChanged();
                    }

                    showStartDateTimePicker(tv_from_date);


                } else {
                    showStartDateTimePicker(tv_from_date);
                }


            }
        });

        tv_from_date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_from_date.getText().toString().isEmpty()) {
                    Toast.makeText(NewApplication.this, "First Select start date", Toast.LENGTH_SHORT).show();
                } else
                    showEndDateTimePicker(tv_from_date1);

            }
        });

        tv_from_date1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    total = 0;
                    date1 = myFormat.parse(startDate);
                    date2 = myFormat.parse(endDate);
                    diff = date2.getTime() - date1.getTime();
                    System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

                    subtotal1 = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    if (date1 != null && date2 != null) {
                        calculateDuration();
                    } else {
                        Toast.makeText(NewApplication.this, "First Select start date", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (date1 == null) {
                    Toast.makeText(NewApplication.this, "First Select start date", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit_for_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker(edit_for_date);
            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        edit_date.setText(mdformat.format(calendar.getTime()));

        ll_time_from = (LinearLayout) findViewById(R.id.ll_time_from);

        spinner_leave.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    leavetext = spinner_leave.getItemAtPosition(spinner_leave.getSelectedItemPosition()).toString();

                    ((TextView) adapterView.getChildAt(0)).setTextSize(16);
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#2A5F3D"));
                    Typeface bahamas = Typeface.createFromAsset(getAssets(), "font/raleway.regular.ttf");
                    ((TextView) adapterView.getChildAt(0)).setTypeface(bahamas);

                    if (leavetext.equalsIgnoreCase("Short Leave")) {

                        ll_date_from.setVisibility(View.GONE);
                        ll_time_from.setVisibility(View.VISIBLE);
                    } else {
                        ll_date_from.setVisibility(View.VISIBLE);
                        ll_time_from.setVisibility(View.GONE);

                    }


                    try {
                        closignBal = closingBalanceList.get(i).toString();
                        applyingfor = applyngforlist.get(i).toString();
                        position = i;
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                    copmpareleaves = memberList.get(position).getLeave();

                    if (afS != null && cbS != null) {

                        memberList.get(lastposition).setOd(String.valueOf(afS));
                        memberList.get(lastposition).setSl(String.valueOf(cbS));
                        memberSLAdapter.notifyDataSetChanged();
                        memberODAdapter.notifyDataSetChanged();


                        if (leavetext.equalsIgnoreCase(copmpareleaves)) {
                            GetLeaveCalculation();

                        }


                    }
                } catch (Exception e) {

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.trans_right_in,
                        R.anim.trans_right_out);
            }
        });
    }

    private void calculateDuration() {
        if (date1 == null || date2 == null) {
            edit_days.setText("");
            total1 = 0;
        } else if (diff < 0) {
            edit_days.setText(" ");
            total1 = 0;
        } else {
            if ((sp_From_status.equals("From First Half") && sp_to_status.equals("To First Half"))) {
                total1 = 0;
                edit_days.setText("");
                sp_From_status1 = 0.5;
                total1 = subtotal1 + sp_From_status1;
                Log.e("duartion1", String.valueOf(total1));
                edit_days.setText(String.valueOf(total1));
                if (leavetext.equalsIgnoreCase(copmpareleaves)) {
                    GetLeaveCalculation();

                }
            } else if ((sp_From_status.equals("From First Half") && sp_to_status.equals("To Second Half"))) {
                total1 = 0;
                edit_days.setText("");

                sp_From_status1 = 1.0;
                total1 = subtotal1 + sp_From_status1;
                Log.e("duartion2", String.valueOf(total1));
                edit_days.setText(String.valueOf(total1));
                if (leavetext.equalsIgnoreCase(copmpareleaves)) {
                    GetLeaveCalculation();

                }
            } else if ((sp_From_status.equals("From Second Half") && sp_to_status.equals("To First Half"))) {
                total1 = 0;
                sp_From_status1 = 0;

                total1 = subtotal1 + sp_From_status1;
                Log.e("duartion3", String.valueOf(total1));
                edit_days.setText(String.valueOf(total1));
                if (leavetext.equalsIgnoreCase(copmpareleaves)) {
                    GetLeaveCalculation();

                }
            } else if (sp_From_status.equals("From Second Half") && sp_to_status.equals("To Second Half")) {
                total1 = 0;
                sp_From_status1 = 0.5;

                total1 = subtotal1 + sp_From_status1;
                Log.e("duartion4", String.valueOf(total1));
                edit_days.setText(String.valueOf(total1));
                if (leavetext.equalsIgnoreCase(copmpareleaves)) {
                    GetLeaveCalculation();

                }
            }
        }
    }

    private void SubmitLeave(String employeeId, String companyId, String approverid, String leaveType,
                             String fromDate, String firstHalf, String toDate, String timeFrom, String timeTo,
                             String leaveAppDate, String secondHalf, String days, String remarks, String status) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        RequestQueue sr1 = Volley.newRequestQueue(NewApplication.this);

        String url = Endpoints.INSERTLEAVEAPP;

        Log.e("DATA", "" + ">>" + employeeId + ">>" + companyId + ">>" + approverid + ">>" + leaveType + ">>" + fromDate + ">>" + firstHalf + ">>" + toDate + ">>" + timeFrom + ">>" + timeTo + ">>" + leaveAppDate + ">>" + secondHalf + ">>" + days + ">>" + remarks + ">>" + status);
//{EmployeeId=1, TimeFrom=10:00, SecondHalf=To Second Half, Status=Submitted, TimeTo=13:00, ToDate=22/08/2019,
// Remarks=nnnjjj, FromDate=22/08/2019, leavebalance=0.0, LeaveType=Short Leave,
// Days=0, flag=, LeaveAppDate=22/08/2019, CompanyId=1, CompanyCode=nipl2, Approver=1, FirstHalf=From First Half}
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("EmployeeId", employeeId);
        jsonParams.put("CompanyId", companyId);
        jsonParams.put("Approver", approverid); //
        jsonParams.put("LeaveType", leaveType); //
        jsonParams.put("FromDate", fromDate); //
        jsonParams.put("FirstHalf", firstHalf); //
        jsonParams.put("ToDate", toDate); //
        jsonParams.put("TimeFrom", sp_From_time_status1 + ":" + sp_From_time_status2); //
        jsonParams.put("TimeTo", sp_to_time_status1 + ":" + sp_to_time_status2); //
        jsonParams.put("LeaveAppDate", leaveAppDate); //
        jsonParams.put("SecondHalf", secondHalf); //
        jsonParams.put("Days", days); //
        jsonParams.put("Remarks", remarks); //
        jsonParams.put("Status", "Submitted"); //
        jsonParams.put("leavebalance", leavebalance); //
        jsonParams.put("flag", ""); //

        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(NewApplication.this, "CompanyCode"));


        Log.e("paramsssss", jsonParams.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("====NewApplication===" + response);
                        Log.e("responsesuuccess", ">>>>>" + response);
                        pd.dismiss();

                        try {
                            pd.dismiss();

                            JSONObject jsonObject = new JSONObject(response.toString());
                            String result = jsonObject.getString("result");
                            String Messages = jsonObject.getString("Messages");

                            if (result.equalsIgnoreCase("true")) {
                                String data = jsonObject.getString("data");

                                SuccessDialog();

                            } else if (result.equalsIgnoreCase("false")) {


                                AlertDialog.Builder builder = new AlertDialog.Builder(NewApplication.this);
                                builder.setMessage(Messages);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        // onBackPressed();
                                    }
                                });


//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
////                            dialogStatus = false;
//            }
//        });


                                AlertDialog alertDialog = builder.create();
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                            } else {
                                //   SuccessDialog();
                                // SuccessDialog();
//                                        onBackPressed();
//                                        Toast.makeText(NewApplication.this, Messages, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            SuccessDialog();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        error.printStackTrace();


                        if (null != error.networkResponse) {
//                            SuccessDialog();
//                                    Toast.makeText(NewApplication.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
//                                    onBackPressed();
                            Log.e("responseerror", ">>>>>" + error);
                            System.out.println("====response===" + error);
                        }
                    }
                });
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        sr1.add(request);
        sr1.getCache().clear();
    }

    private void SuccessDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NewApplication.this);
        builder.setMessage("Successfully Submitted");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(NewApplication.this, LeaveApplication.class);
                intent.putExtra("from", "application");
                startActivity(intent);
                // onBackPressed();
            }
        });


//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
////                            dialogStatus = false;
//            }
//        });


        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }


    private void showEndDateTimePicker(final TextView tv_from_date1) {
        final Calendar date1;

//        yyyy-MM-dd hh:mm:ss aa
        final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar currentDate = Calendar.getInstance();
        date1 = Calendar.getInstance();


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        date1.set(year, monthOfYear, dayOfMonth);
                        Log.e("DATE_TIME", "The choosen one " + date1.getTime());
                        endDate1 = String.valueOf(formate.format(date1.getTime()));
                        Log.e("NEW_DATE_TIME", "The choosen one " + endDate1);
                        String DATE_FORMAT_I = "yyyy-MM-dd";
                        String DATE_FORMAT_O = "dd/MM/yyyy";


                        SimpleDateFormat formatInput = new SimpleDateFormat(DATE_FORMAT_I);
                        SimpleDateFormat formatOutput = new SimpleDateFormat(DATE_FORMAT_O);

                        Date date = null;
                        try {
                            date = formatInput.parse(endDate1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        endDate = formatOutput.format(date);

                        // Log.e("NEW_DATE_TIME", "The choosen one " + endDate1);

                        tv_from_date1.setText(endDate);
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));
        // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void showStartDateTimePicker(final TextView tv_from_date) {
        final Calendar date1;
        final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar currentDate = Calendar.getInstance();
        date1 = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        date1.set(year, monthOfYear, dayOfMonth);
                        //  Toast.makeText(NewApplication.this, ">>>"+monthOfYear, Toast.LENGTH_SHORT).show();
                        Log.e("DATE_TIME", "The choosen one " + date1.getTime());


                        startDate1 = String.valueOf(formate.format(date1.getTime()));
                        String DATE_FORMAT_I = "yyyy-MM-dd";
                        String DATE_FORMAT_O = "dd/MM/yyyy";


                        SimpleDateFormat formatInput = new SimpleDateFormat(DATE_FORMAT_I);
                        SimpleDateFormat formatOutput = new SimpleDateFormat(DATE_FORMAT_O);

                        Date date = null;
                        try {
                            date = formatInput.parse(startDate1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        startDate = formatOutput.format(date);


                        //  Log.e("NEW_DATE_TIME", "The choosen one " + startDate1);
                        tv_from_date.setText(startDate);

                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));
        // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    public void showDateTimePicker(final TextView tv_time) {
        final Calendar date1;

//        yyyy-MM-dd hh:mm:ss aa
        final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar currentDate = Calendar.getInstance();
        date1 = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date1.set(year, monthOfYear, dayOfMonth);
                        Log.e("DATE_TIME", "The choosen one " + date1.getTime());


                        forDate1 = String.valueOf(formate.format(date1.getTime()));

                        String DATE_FORMAT_I = "yyyy-MM-dd";
                        String DATE_FORMAT_O = "dd/MM/yyyy";


                        SimpleDateFormat formatInput = new SimpleDateFormat(DATE_FORMAT_I);
                        SimpleDateFormat formatOutput = new SimpleDateFormat(DATE_FORMAT_O);

                        Date date = null;
                        try {
                            date = formatInput.parse(forDate1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        forDate = formatOutput.format(date);


                        Log.e("NEW_DATE_TIME", "The choosen one " + forDate1);
                        tv_time.setText(forDate);

                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));
        //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    private void GetLeaveCalculation() {

        try {


            Double af = Double.valueOf(applyingfor) + total1;
            Double cb = Double.valueOf(closignBal) - total1;
            leavebalance = String.valueOf(cb);
            memberList.get(position).setOd(String.valueOf(af));
            memberList.get(position).setSl(String.valueOf(cb));
            lastposition = position;
            cbS = closignBal;
            afS = applyingfor;
            Log.e("applyingfor  ", afS);
            Log.e("closignBal  ", cbS);

            memberSLAdapter.notifyDataSetChanged();
            memberODAdapter.notifyDataSetChanged();

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }


    }


    private void GetMemberLeavesDetails() {
        final ProgressDialog pd2 = new ProgressDialog(NewApplication.this);
        pd2.setMessage("Loading Please Wait...");
        pd2.setCanceledOnTouchOutside(false);
        pd2.show();
        RequestQueue sr1 = Volley.newRequestQueue(NewApplication.this);

        // String url1 = "http://103.205.65.52/MobileApp/GetMemberLeavesDetails";
        String url1 = Endpoints.LEAVEDETAILS;

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("EmployeeId", AppPreferences.loadPreferences(NewApplication.this, "EmployeeId"));
        jsonParams.put("CompanyId", AppPreferences.loadPreferences(NewApplication.this, "CompanyId"));
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(NewApplication.this, "CompanyCode"));
        Log.e("jsonParams", ">>>>>" + jsonParams.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url1, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("GetMemberLeavesDetails", ">>>>>" + response);
                        System.out.println("====GetMemberLeavesDetails===" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            //   String result = jsonObject.getString("result");
//                            if (result.equalsIgnoreCase("true")) {
                            pd2.dismiss();
                            JSONArray data = jsonObject.getJSONArray("data");
                            JSONArray data1 = jsonObject.getJSONArray("data1");
                            JSONObject dataobj1 = data1.getJSONObject(0);
                            double slt = dataobj1.getDouble("short_leave_time");

                            short_leave_time = (int) Math.round(slt);
                            calculateFromTimepinner();
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject dataobj = data.getJSONObject(i);
                                String leave = dataobj.getString("LeaveType");
                                String PL = dataobj.getString("CurrentBalance");
                                String CL = dataobj.getString("AllreadyApplied");
                                String SL = dataobj.getString("AvailableBalance");
                                String OD = dataobj.getString("ApplyingFor");
                                String CO = dataobj.getString("ClosingBalance");
                                boolean Status = dataobj.getBoolean("Status");
                                leavearraylist.add(leave);
                                if (Status) {
                                    applyngforlist.add(OD);
                                    closingBalanceList.add(CO);
                                }


                                if (Status) {


                                    memberList.add(new MemberLeavesBean(leave, PL, CL, SL, OD, CO, Status));

                                    leavet = leave;
                                    currentb = PL;
                                    alreadya = CL;
                                    availableb = SL;
                                    applyingf = OD;
                                    closingb = CO;
                                    Log.e("CloB", ">>>" + closingb);


                                }
//                                else
//                                {
//                                    memberList.add(new MemberLeavesBean("dummy", "dummy", "dummy", "dummy",
//                                            "dummy", "dummy",false));
//                                    hashSet.addAll(memberList);
//                                    memberList.clear();
//                                    memberList.addAll(hashSet);
//
//                                }

                            }
                            spinner_leave.setAdapter(leavearrayadapter);

                            memberLeavesAdapter = new MemberLeavesAdapter(NewApplication.this, memberList);
                            gv_AA.setAdapter(memberLeavesAdapter);
                            gv_AA.setExpanded(true);
                            memberLeavesAdapter.notifyDataSetChanged();

                            memberCOAdapter = new MemberCOAdapter(NewApplication.this, memberList);
                            gv_CTB.setAdapter(memberCOAdapter);
                            gv_CTB.setExpanded(true);
                            memberCOAdapter.notifyDataSetChanged();

                            memberPLAdapter = new MemberPLAdapter(NewApplication.this, memberList);
                            gv_AB.setAdapter(memberPLAdapter);
                            gv_AB.setExpanded(true);
                            memberPLAdapter.notifyDataSetChanged();

                            memberSLAdapter = new MemberSLAdapter(NewApplication.this, memberList);
                            gv_CB.setAdapter(memberSLAdapter);
                            gv_CB.setExpanded(true);
                            memberSLAdapter.notifyDataSetChanged();

                            memberODAdapter = new MemberODAdapter(NewApplication.this, memberList);
                            gv_AF.setAdapter(memberODAdapter);
                            gv_AF.setExpanded(true);
                            memberODAdapter.notifyDataSetChanged();

                            memberLTAdapter = new MemberLTAdapter(NewApplication.this, memberList);
                            gv_LT.setAdapter(memberLTAdapter);
                            gv_LT.setExpanded(true);
                            memberLTAdapter.notifyDataSetChanged();


//                                afO2= (mainList.get(2).getOd());
//                                cbO2=(mainList.get(2).getSl());
//                                afO3= (mainList.get(3).getOd());
//                                cbO3=(mainList.get(3).getSl());
//                            } else {
//                                pd2.dismiss();
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd2.dismiss();
                        if (null != error.networkResponse) {
                            Toast.makeText(NewApplication.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void calculateFromTimepinner() {
        for (int i = 8; i <= 24 - short_leave_time; i++) {
            fromtimelist.add(String.valueOf(i));
            sp_From_time1.setAdapter(fromtimeadapter);
            sp_to_time1.setText(String.valueOf(10 + short_leave_time));

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.trans_right_in,
//                R.anim.trans_right_out);
        Intent intent = new Intent(NewApplication.this, LeaveApplication.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    private class MemberLeavesAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<MemberLeavesBean> memberLeavesBeans;


        MemberLeavesAdapter(NewApplication newApplication, ArrayList<MemberLeavesBean> memberLeavesBeans) {

            this.context = newApplication;
            inflater = LayoutInflater.from(context);
            this.memberLeavesBeans = memberLeavesBeans;

        }

        @Override
        public int getCount() {
            return memberLeavesBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return memberLeavesBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {
            TextView tv_leave;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.member_leave, parent, false);
                holder.tv_leave = (TextView) view.findViewById(R.id.tv_leave);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            if (memberLeavesBeans.get(position).getCl() != "dummy") {
                holder.tv_leave.setText(memberLeavesBeans.get(position).getCl());

            }


            return view;
        }
    }

    private class MemberCOAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<MemberLeavesBean> memberLeavesBeans;


        public MemberCOAdapter(NewApplication newApplication, ArrayList<MemberLeavesBean> memberLeavesBeans) {

            this.context = newApplication;
            inflater = LayoutInflater.from(context);
            this.memberLeavesBeans = memberLeavesBeans;

        }

        @Override
        public int getCount() {
            return memberLeavesBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return memberLeavesBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_leave;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.member_leave, parent, false);
                holder.tv_leave = (TextView) view.findViewById(R.id.tv_leave);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            if (memberLeavesBeans.get(position).getCo() != "dummy") {
                holder.tv_leave.setText(memberLeavesBeans.get(position).getCo());

            }


            return view;
        }
    }

    private class MemberPLAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<MemberLeavesBean> memberLeavesBeans;


        public MemberPLAdapter(NewApplication newApplication, ArrayList<MemberLeavesBean> memberLeavesBeans) {

            this.context = newApplication;
            inflater = LayoutInflater.from(context);
            this.memberLeavesBeans = memberLeavesBeans;

        }

        @Override
        public int getCount() {
            return memberLeavesBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return memberLeavesBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_leave;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.member_leave, parent, false);
                holder.tv_leave = (TextView) view.findViewById(R.id.tv_leave);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }

            if (memberLeavesBeans.get(position).getPl() != "dummy") {
                holder.tv_leave.setText(memberLeavesBeans.get(position).getPl());

            }


            return view;
        }
    }

    private class MemberSLAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<MemberLeavesBean> memberLeavesBeans;


        public MemberSLAdapter(NewApplication newApplication, ArrayList<MemberLeavesBean> memberLeavesBeans) {

            this.context = newApplication;
            inflater = LayoutInflater.from(context);
            this.memberLeavesBeans = memberLeavesBeans;

        }

        @Override
        public int getCount() {
            return memberLeavesBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return memberLeavesBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_leave;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.member_leave, parent, false);
                holder.tv_leave = (TextView) view.findViewById(R.id.tv_leave);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            if (memberLeavesBeans.get(position).getSl() != "dummy") {
                holder.tv_leave.setText(memberLeavesBeans.get(position).getSl());

            }


            return view;
        }
    }

    private class MemberODAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<MemberLeavesBean> memberLeavesBeans;


        MemberODAdapter(NewApplication newApplication, ArrayList<MemberLeavesBean> memberLeavesBeans) {

            this.context = newApplication;
            inflater = LayoutInflater.from(context);
            this.memberLeavesBeans = memberLeavesBeans;

        }

        @Override
        public int getCount() {
            return memberLeavesBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return memberLeavesBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_leave;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.member_leave, parent, false);
                holder.tv_leave = (TextView) view.findViewById(R.id.tv_leave);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            if (memberLeavesBeans.get(position).getOd() != "dummy") {
                holder.tv_leave.setText(memberLeavesBeans.get(position).getOd());

            }


            return view;
        }
    }

    private class MemberLTAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<MemberLeavesBean> memberLeavesBeans;


        MemberLTAdapter(NewApplication newApplication, ArrayList<MemberLeavesBean> memberLeavesBeans) {

            this.context = newApplication;
            inflater = LayoutInflater.from(context);
            this.memberLeavesBeans = memberLeavesBeans;
        }

        @Override
        public int getCount() {
            return memberLeavesBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return memberLeavesBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_leave;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.member_leave, parent, false);
                holder.tv_leave = (TextView) view.findViewById(R.id.tv_leave);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }

            if (memberLeavesBeans.get(position).getLeave() != "dummy") {
                Log.e("leave", memberLeavesBeans.get(position).getLeave());

                holder.tv_leave.setText(memberLeavesBeans.get(position).getLeave());

            }


            return view;
        }
    }


}
