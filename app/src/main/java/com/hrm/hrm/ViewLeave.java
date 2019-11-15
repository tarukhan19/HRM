package com.hrm.hrm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightGridView;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.hrm.hrm.Bean.MemberLeavesBean;
import com.hrm.hrm.Bean.leaveApplicationBean;
import com.hrm.hrm.utils.AppPreferences;
import com.hrm.hrm.utils.Endpoints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ViewLeave extends Activity {
    TextView tv_emp_code, tv_emp_name, tv_approver, tv_app_date, tv_leave_type, tv_from_date, tv_from_type,
            tv_to_date, tv_to_type, tv_days, tv_remark, tv_app_remark,employeeName,employeeId;
    LinearLayout ll_back, ll_close, ll_accept, ll_reject, ll_accRej, ll_cancel, apprRemarkLL,ll1,ll2;
    ProgressDialog pd;
    String flag, falgValue, date, ApproverStatus;
    ArrayList<MemberLeavesBean> memberLeavesBeans;
    MemberLeavesAdapter memberLeavesAdapter;
    MemberCOAdapter memberCOAdapter;
    MemberPLAdapter memberPLAdapter;
    MemberSLAdapter memberSLAdapter;
    MemberODAdapter memberODAdapter;
    MemberLTAdapter memberLTAdapter;
    ScrollView sc_view;
    String value, remark, SNo, value1;
    String data;
    ExpandableHeightListView gv_data, gv_co, gv_PL, gv_SL, gv_OD, gv_LT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_leave);
        String Cid = AppPreferences.loadPreferences(ViewLeave.this, "CompanyId");

        String CompanyCode= AppPreferences.loadPreferences(ViewLeave.this, "CompanyCode");

        Endpoints endpoints=new Endpoints(CompanyCode);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#A5C1EB"));
        }

        Intent intent = getIntent();
        value = intent.getStringExtra("value");
        value1 = intent.getStringExtra("value1");
//        Log.e("value1",value1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(new Date());
        Log.e("date", date);

        memberLeavesBeans = new ArrayList<>();
        sc_view = (ScrollView) findViewById(R.id.sc_view);
        ll_close = (LinearLayout) findViewById(R.id.ll_close);
        gv_data = (ExpandableHeightListView) findViewById(R.id.gv_data);
        gv_co = (ExpandableHeightListView) findViewById(R.id.gv_co);
        gv_PL = (ExpandableHeightListView) findViewById(R.id.gv_PL);
        gv_SL = (ExpandableHeightListView) findViewById(R.id.gv_SL);
        gv_OD = (ExpandableHeightListView) findViewById(R.id.gv_OD);
        gv_LT = (ExpandableHeightListView) findViewById(R.id.gv_LT);
        employeeName=findViewById(R.id.employeeName);
        employeeId=findViewById(R.id.employeeId);
        ll1=findViewById(R.id.ll1);
        ll2=findViewById(R.id.ll2);


        apprRemarkLL = findViewById(R.id.apprRemarkLL);

        GetMemberLeavesDetails();
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_accRej = (LinearLayout) findViewById(R.id.ll_accRej);
        ll_accept = (LinearLayout) findViewById(R.id.ll_accept);
        ll_reject = (LinearLayout) findViewById(R.id.ll_reject);
        ll_cancel = (LinearLayout) findViewById(R.id.ll_cancel);

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.trans_right_in,
                        R.anim.trans_right_out);
            }
        });
        ll_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.trans_right_in,
                        R.anim.trans_right_out);
            }
        });

        ll_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                falgValue = "1";
                accepApplication(falgValue);
            }
        });

        ll_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                falgValue = "0";
                accepApplication(falgValue);
            }
        });

        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                falgValue = "2";
                accepApplication(falgValue);
            }
        });

        tv_emp_code = (TextView) findViewById(R.id.tv_emp_code);
        tv_emp_name = (TextView) findViewById(R.id.tv_emp_name);
        tv_approver = (TextView) findViewById(R.id.tv_approver);
        tv_app_date = (TextView) findViewById(R.id.tv_app_date);
        tv_leave_type = (TextView) findViewById(R.id.tv_leave_type);
        tv_from_date = (TextView) findViewById(R.id.tv_from_date);
        tv_from_type = (TextView) findViewById(R.id.tv_from_type);
        tv_to_date = (TextView) findViewById(R.id.tv_to_date);
        tv_to_type = (TextView) findViewById(R.id.tv_to_type);
        tv_days = (TextView) findViewById(R.id.tv_days);
        tv_remark = (TextView) findViewById(R.id.tv_remark);
        tv_app_remark = (TextView) findViewById(R.id.tv_app_remark);


        flag = "View";
        GetLeaveApplicationForApprove(flag);


    }

    private void accepApplication(String flagVal) {
        pd = new ProgressDialog(ViewLeave.this);
        pd.setMessage("Loading Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        RequestQueue sr1 = Volley.newRequestQueue(ViewLeave.this);

        String url = Endpoints.ACCEPTREJECT;
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(ViewLeave.this, "CompanyCode"));
        jsonParams.put("EmployeeId", AppPreferences.loadPreferences(ViewLeave.this, "EmployeeId"));
        jsonParams.put("CompanyId", AppPreferences.loadPreferences(ViewLeave.this, "CompanyId"));
        jsonParams.put("StatusDate", date);
        jsonParams.put("flag", flagVal);
        jsonParams.put("SrNo", SNo);
        jsonParams.put("StatusRemark", remark);


        Log.e("values", ">>" + jsonParams);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("accepApplication", ">>>>>" + response);
//{"result":true,"Messages":"Successfull","data":"rejected"}

                        try {
                            pd.dismiss();
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            boolean result = jsonObject.getBoolean("result");
                            String Messages = jsonObject.getString("Messages");
                            data = jsonObject.getString("data");
                            if (result == true && Messages.equalsIgnoreCase("Successfull")) {
                                if (data.equalsIgnoreCase("rejected")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewLeave.this);
                                    builder.setMessage("Rejected");
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ViewLeave.this, LeaveApplication.class);
                                            startActivity(intent);
                                            // onBackPressed();
                                        }
                                    });

                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();
                                } else if (data.equalsIgnoreCase("9_Cancel")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewLeave.this);
                                    builder.setMessage("Cancelled");
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ViewLeave.this, LeaveApplication.class);
                                            startActivity(intent);
                                            // onBackPressed();
                                        }
                                    });

                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewLeave.this);
                                    builder.setMessage("Accepted");
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ViewLeave.this, LeaveApplication.class);
                                            startActivity(intent);
                                            // onBackPressed();
                                        }
                                    });


                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();
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
                            Toast.makeText(ViewLeave.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void GetMemberLeavesDetails() {


        final ProgressDialog pd2 = new ProgressDialog(ViewLeave.this);
        pd2.setMessage("Loading Please Wait...");
        pd2.setCanceledOnTouchOutside(false);
        pd2.show();

        RequestQueue sr1 = Volley.newRequestQueue(ViewLeave.this);

        // String url1 = "http://103.205.65.52/MobileApp/GetMemberLeavesDetails";
        String url1 = Endpoints.LEAVEDETAILS;

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("EmployeeId", AppPreferences.loadPreferences(ViewLeave.this, "EmployeeId"));
        jsonParams.put("CompanyId", AppPreferences.loadPreferences(ViewLeave.this, "CompanyId"));
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(ViewLeave.this, "CompanyCode"));

//        jsonParams.put("EmployeeId", "1");
//       jsonParams.put("CompanyId", "1");
//        jsonParams.put("LeaveType","co");
//        jsonParams.put("Days", "3");
//        jsonParams.put("MonthId","1");


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url1, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("GetMemberLeavesDetails", ">>>>>" + response);

                        try {

                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            String result = jsonObject.getString("result");
                            String Messages = jsonObject.getString("Messages");
                            if (result.equalsIgnoreCase("true")) {
                                pd2.dismiss();
                                JSONArray data = jsonObject.getJSONArray("data");

                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject dataobj = data.getJSONObject(i);
                                    String leave = dataobj.getString("LeaveType");
                                    String PL = dataobj.getString("CurrentBalance");
                                    String CL = dataobj.getString("AllreadyApplied");
                                    String SL = dataobj.getString("AvailableBalance");
                                    String OD = dataobj.getString("ApplyingFor");
                                    String CO = dataobj.getString("ClosingBalance");
                                    boolean Status=dataobj.getBoolean("Status");
                                    memberLeavesBeans.add(new MemberLeavesBean(leave, PL, CL, SL, OD, CO,Status));

                                }
                                memberLeavesAdapter = new MemberLeavesAdapter(ViewLeave.this, memberLeavesBeans);
                                gv_data.setAdapter(memberLeavesAdapter);
                                gv_data.setExpanded(true);
                                memberLeavesAdapter.notifyDataSetChanged();

                                memberCOAdapter = new MemberCOAdapter(ViewLeave.this, memberLeavesBeans);
                                gv_co.setAdapter(memberCOAdapter);
                                gv_co.setExpanded(true);
                                memberCOAdapter.notifyDataSetChanged();

                                memberPLAdapter = new MemberPLAdapter(ViewLeave.this, memberLeavesBeans);
                                gv_PL.setAdapter(memberPLAdapter);
                                gv_PL.setExpanded(true);
                                memberPLAdapter.notifyDataSetChanged();

                                memberSLAdapter = new MemberSLAdapter(ViewLeave.this, memberLeavesBeans);
                                gv_SL.setAdapter(memberSLAdapter);
                                gv_SL.setExpanded(true);
                                memberSLAdapter.notifyDataSetChanged();

                                memberODAdapter = new MemberODAdapter(ViewLeave.this, memberLeavesBeans);
                                gv_OD.setAdapter(memberODAdapter);
                                gv_OD.setExpanded(true);
                                memberODAdapter.notifyDataSetChanged();

                                memberLTAdapter = new MemberLTAdapter(ViewLeave.this, memberLeavesBeans);
                                gv_LT.setAdapter(memberLTAdapter);
                                gv_LT.setExpanded(true);
                                memberLTAdapter.notifyDataSetChanged();


                            } else {


                                pd2.dismiss();

                            }

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
                            Toast.makeText(ViewLeave.this, "Error", Toast.LENGTH_SHORT).show();
                            System.out.println("====response===" + error);
                        }
                    }
                });
        sr1.add(request);
    }


    private void  GetLeaveApplicationForApprove(String flag) {


        pd = new ProgressDialog(ViewLeave.this);
        pd.setMessage("Loading Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        RequestQueue sr2 = Volley.newRequestQueue(ViewLeave.this);

        String url = Endpoints.LEAVEAPPAPPROVE;


        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("EmployeeId", AppPreferences.loadPreferences(ViewLeave.this, "EmployeeId"));
        Log.e("EmployeeId", AppPreferences.loadPreferences(ViewLeave.this, "EmployeeId"));
        jsonParams.put("CompanyId", AppPreferences.loadPreferences(ViewLeave.this, "CompanyId"));
        Log.e("CompanyId", AppPreferences.loadPreferences(ViewLeave.this, "CompanyId"));
        jsonParams.put("SrNo", AppPreferences.loadPreferences(ViewLeave.this, "SRNO"));
        Log.e("SrNo", AppPreferences.loadPreferences(ViewLeave.this, "SRNO"));
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(ViewLeave.this, "CompanyCode"));

        jsonParams.put("Flag", flag);
        Log.e("Flag", flag);
        jsonParams.put("Status", "");


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("====GET_LEAVE_View===" + response);
                        Log.e("GET_LEAVE_View", ">>>>>>" + response);
                        pd.dismiss();

                        try {
                            pd.dismiss();

                            String result = response.getString("result");
                            String Messages = response.getString("Messages");


                            if (result.equalsIgnoreCase("true")) {

                                pd.dismiss();


                                JSONArray data = response.getJSONArray("data");


                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject dataobj = data.getJSONObject(0);
                                    pd.dismiss();

                                    SNo = dataobj.getString("SNo");
                                    String Emp_Code = dataobj.getString("Emp_Code");
                                    String EmployeeId = dataobj.getString("EmployeeId");
                                    String EmployeeName = dataobj.getString("EmployeeName");
                                    String Approver = dataobj.getString("Approver");
                                    String LeaveType = dataobj.getString("LeaveType");
                                    String FromDate = dataobj.getString("FromDate");
                                    String LeaveAppDate = dataobj.getString("LeaveAppDate");
                                    String FirstHalf = dataobj.getString("FirstHalf");
                                    String ToDate = dataobj.getString("ToDate");
                                    String SecondHalf = dataobj.getString("SecondHalf");
                                    String TimeFrom = dataobj.getString("TimeFrom");
                                    String TimeTo = dataobj.getString("TimeTo");
                                    String Days = dataobj.getString("Days");
                                    String Remarks = dataobj.getString("Remarks");
                                    remark = Remarks;
                                    String Statusnew = dataobj.getString("Status");
                                    String LeaveAppCount = dataobj.getString("LeaveAppCount");
                                    ApproverStatus = dataobj.getString("ApproverStatus");

                                    String DATE_FORMAT_I = "yyyy-MM-dd'T'HH:mm:ss";
                                    String DATE_FORMAT_O = "dd-MM-yyyy";


                                    SimpleDateFormat formatInput = new SimpleDateFormat(DATE_FORMAT_I);
                                    SimpleDateFormat formatOutput = new SimpleDateFormat(DATE_FORMAT_O);

                                    Date date1 = null;
                                    Date date2 = null;
                                    Date date3 = null;
                                    try {
                                        date1 = formatInput.parse(FromDate);
                                        date2 = formatInput.parse(ToDate);
                                        date3 = formatInput.parse(LeaveAppDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    // dateString = formatOutput.format(date2);

                                    if (LeaveType.equalsIgnoreCase("short leave"))
                                    {
                                        ll1.setVisibility(View.GONE);
                                        ll2.setVisibility(View.GONE);
                                        tv_from_date.setText(TimeFrom);
                                        tv_to_date.setText(TimeTo);
                                    }
                                    else
                                    {
                                        ll1.setVisibility(View.VISIBLE);
                                        ll2.setVisibility(View.VISIBLE);
                                        tv_from_date.setText(formatOutput.format(date1));
                                        tv_to_date.setText(formatOutput.format(date2));

                                    }

                                    tv_emp_code.setText(EmployeeId);
                                    tv_emp_name.setText(EmployeeName);
                                    tv_approver.setText(Approver);
                                    tv_leave_type.setText(LeaveType);
                                    tv_app_date.setText(formatOutput.format(date3));
                                    tv_from_type.setText(FirstHalf);
                                    tv_to_type.setText(SecondHalf);
                                    tv_days.setText(Days);
                                    tv_remark.setText(Remarks);
                                    employeeId.setText(Emp_Code);
                                    employeeName.setText(EmployeeName);

                                    if (Statusnew.equalsIgnoreCase("Submitted")) {
                                        apprRemarkLL.setVisibility(View.INVISIBLE);
                                    } else {
                                        apprRemarkLL.setVisibility(View.VISIBLE);
                                        tv_app_remark.setText(Statusnew);
                                    }


                                    if (value.equalsIgnoreCase("2") &&
                                            ApproverStatus.equalsIgnoreCase("Final Approve")) {
                                      //  ll_cancel.setVisibility(View.VISIBLE);
                                        ll_accRej.setVisibility(View.GONE);
                                        ll_cancel.setVisibility(View.GONE);

                                    } else if (value.equalsIgnoreCase("0")
                                            && value1.equalsIgnoreCase("true")) {
                                        ll_accRej.setVisibility(View.VISIBLE);
                                    } else if (value.equalsIgnoreCase("1")) {
                                        ll_accRej.setVisibility(View.GONE);
                                    } else {
                                        ll_cancel.setVisibility(View.GONE);
                                        ll_accRej.setVisibility(View.GONE);
                                    }
                                    pd.dismiss();
                                }


                                pd.dismiss();

                            } else {

                                pd.dismiss();
                            }


                        } catch (JSONException e) {
                            pd.dismiss();
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        if (null != error.networkResponse) {
                            pd.dismiss();
                            System.out.println("====response===" + error);
                        }
                    }
                });
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        sr2.add(request);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);
    }

    private class MemberLeavesAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<MemberLeavesBean> memberLeavesBeans;


        public MemberLeavesAdapter(ViewLeave newApplication, ArrayList<MemberLeavesBean> memberLeavesBeans) {

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
            holder.tv_leave.setText(memberLeavesBeans.get(position).getCl());


            return view;
        }
    }

    private class MemberCOAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<MemberLeavesBean> memberLeavesBeans;


        public MemberCOAdapter(ViewLeave newApplication, ArrayList<MemberLeavesBean> memberLeavesBeans) {

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
            holder.tv_leave.setText(memberLeavesBeans.get(position).getCo());


            return view;
        }
    }

    private class MemberPLAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<MemberLeavesBean> memberLeavesBeans;


        public MemberPLAdapter(ViewLeave newApplication, ArrayList<MemberLeavesBean> memberLeavesBeans) {

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
            holder.tv_leave.setText(memberLeavesBeans.get(position).getPl());


            return view;
        }
    }

    private class MemberSLAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<MemberLeavesBean> memberLeavesBeans;


        public MemberSLAdapter(ViewLeave newApplication, ArrayList<MemberLeavesBean> memberLeavesBeans) {

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
            holder.tv_leave.setText(memberLeavesBeans.get(position).getSl());


            return view;
        }
    }

    private class MemberODAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<MemberLeavesBean> memberLeavesBeans;


        public MemberODAdapter(ViewLeave newApplication, ArrayList<MemberLeavesBean> memberLeavesBeans) {

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
            holder.tv_leave.setText(memberLeavesBeans.get(position).getOd());


            return view;
        }
    }


    private class MemberLTAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<MemberLeavesBean> memberLeavesBeans;


        public MemberLTAdapter(ViewLeave newApplication, ArrayList<MemberLeavesBean> memberLeavesBeans) {

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
            holder.tv_leave.setText(memberLeavesBeans.get(position).getLeave());


            return view;
        }
    }
}
