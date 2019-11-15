package com.hrm.hrm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TabLayout;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.hrm.hrm.Bean.NewsLetterBean;
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

public class LeaveApplication extends Activity
{

    LinearLayout ll_back,verticalll;
    ArrayList<leaveApplicationBean> leaveApplicationBeans;

    TextView tv_lv_app_received, tv_lv_my_app;
    Spinner spinner_status, spinner_month;
    String statusnew="", statusnew1="Submitted";
    ProgressDialog pd, pd1, pd2;
    ExpandableHeightListView lv_app_received, lv_my_app;
    TextView tv_application_rec, tv_my_application, tv_count1;
    LinearLayout ll_application_rec, ll_tv_my_application, ll_application_rec_btn, ll_tv_my_application_btn,ll_closeLL,ll_close;
    String status, flag;
    LinearLayout ll_my_application, ll_application, ll_new_application, ll_count1;
    LeaveApplicationAdapter leaveApplicationAdapter;
    boolean check=false;
    String Cid,value1="true";
    RelativeLayout rl_success, rl_error;
    String isleave_approver;
    Intent intent;

    boolean cancelVisiblilty=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_application);

        intent=getIntent();



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#A5C1EB"));

        }

        verticalll=findViewById(R.id.verticalll);
        isleave_approver=AppPreferences.loadPreferences(LeaveApplication.this, "leave_approver");
        Cid = AppPreferences.loadPreferences(LeaveApplication.this, "CompanyId");

        String CompanyCode= AppPreferences.loadPreferences(LeaveApplication.this, "CompanyCode");

        Endpoints endpoints=new Endpoints(CompanyCode);



        leaveApplicationBeans = new ArrayList<>();
        lv_app_received = (ExpandableHeightListView) findViewById(R.id.lv_app_received);
        lv_app_received.setExpanded(true);
        lv_my_app = (ExpandableHeightListView) findViewById(R.id.lv_my_app);
        lv_my_app.setExpanded(true);
        tv_count1 = (TextView) findViewById(R.id.tv_count1);
        ll_closeLL=findViewById(R.id.my_ll_close);
        ll_close=findViewById(R.id.ll_close);
        tv_lv_app_received = (TextView) findViewById(R.id.tv_lv_app_received);
        tv_lv_my_app = (TextView) findViewById(R.id.tv_lv_my_app);
        spinner_status = (Spinner) findViewById(R.id.spinner_status);
        spinner_month = (Spinner) findViewById(R.id.spinner_month);
        ll_my_application = (LinearLayout) findViewById(R.id.ll_my_application);
        ll_application = (LinearLayout) findViewById(R.id.ll_application);
        ll_new_application = (LinearLayout) findViewById(R.id.ll_new_application);
        rl_success = (RelativeLayout) findViewById(R.id.rl_success);
        rl_error = (RelativeLayout) findViewById(R.id.rl_error);
        ll_application=findViewById(R.id.ll_application);
        tv_application_rec = (TextView) findViewById(R.id.tv_application_rec);
        tv_my_application = (TextView) findViewById(R.id.tv_my_application);
        ll_application_rec = (LinearLayout) findViewById(R.id.ll_application_rec);
        ll_tv_my_application = (LinearLayout) findViewById(R.id.ll_tv_my_application);
        ll_application_rec_btn = (LinearLayout) findViewById(R.id.ll_application_rec_btn);
        ll_tv_my_application_btn = (LinearLayout) findViewById(R.id.ll_tv_my_application_btn);

        tv_application_rec.setTextSize(16);
        tv_application_rec.setTextColor(Color.parseColor("#2A5F3D"));
        ll_count1 = (LinearLayout) findViewById(R.id.ll_count1);




        if (isleave_approver.equalsIgnoreCase("true"))
        {
            ll_application.setVisibility(View.VISIBLE);
            ll_application_rec_btn.setVisibility(View.VISIBLE);

            if (intent.hasExtra("from"))
            {
                flag = "MyApplicationList";
                if (check==true){ GetLeaveApplicationForApprove(status, flag);}


                ll_tv_my_application_btn.setBackgroundColor(getResources().getColor(R.color.grey));
                ll_application_rec_btn.setBackgroundColor(getResources().getColor(R.color.White));
                tv_my_application.setTextSize(16);
                tv_application_rec.setTextSize(16);
                value1="false";
                tv_application_rec.setTextColor(Color.parseColor("#000000"));
                tv_my_application.setTextColor(Color.parseColor("#2A5F3D"));
                ll_application_rec.setVisibility(View.GONE);
                ll_tv_my_application.setVisibility(View.VISIBLE);
                ll_application.setVisibility(View.GONE);
                ll_my_application.setVisibility(View.VISIBLE);
            }
            else
            {
                flag = "List";
                ll_application_rec_btn.setBackgroundColor(getResources().getColor(R.color.grey));
                ll_tv_my_application_btn.setBackgroundColor(getResources().getColor(R.color.White));

            }
        }

        else
        {
            ll_application.setVisibility(View.GONE);
            ll_application_rec_btn.setVisibility(View.GONE);
            verticalll.setVisibility(View.GONE);
            flag = "MyApplicationList";
            if (check==true){ GetLeaveApplicationForApprove(status, flag);}

            tv_my_application.setTextSize(16);
            tv_application_rec.setTextSize(16);
            value1="false";
            tv_application_rec.setTextColor(Color.parseColor("#000000"));
            tv_my_application.setTextColor(Color.parseColor("#2A5F3D"));
            ll_application_rec.setVisibility(View.GONE);
            ll_tv_my_application.setVisibility(View.VISIBLE);
            ll_application.setVisibility(View.GONE);
            ll_my_application.setVisibility(View.VISIBLE);
        }


        spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                statusnew = spinner_status.getSelectedItem().toString();
               /// Toast.makeText(LeaveApplication.this, statusnew, Toast.LENGTH_SHORT).show();
                ((TextView) adapterView.getChildAt(0)).setTextSize((float) 16);
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#2A5F3D"));

                //Toast.makeText(LeaveApplication.this, statusnew, Toast.LENGTH_SHORT).show();

                if (statusnew.equals("Submitted")) {

                    status = "Submitted";
                    GetLeaveApplicationForApprove(status, flag);
                    ll_close.setVisibility(View.INVISIBLE);
                    cancelVisiblilty=false;


                } else if (statusnew.equals("Approved")) {
                    status = "Approved";
                    GetLeaveApplicationForApprove(status, flag);
                    ll_close.setVisibility(View.INVISIBLE);
                    cancelVisiblilty=false;


                } else if (statusnew.equals("Rejected")) {
                    status = "Rejected";
                    GetLeaveApplicationForApprove(status, flag);
                    ll_close.setVisibility(View.INVISIBLE);
                    cancelVisiblilty=false;


                } else if (statusnew.equals("Cancelled")) {

                    status = "Cancel";
                    GetLeaveApplicationForApprove(status, flag);
                    ll_close.setVisibility(View.INVISIBLE);
                    cancelVisiblilty=false;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                statusnew1 = spinner_month.getSelectedItem().toString();
            ///    Toast.makeText(LeaveApplication.this, statusnew, Toast.LENGTH_SHORT).show();
                ((TextView) adapterView.getChildAt(0)).setTextSize(16);
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#2A5F3D"));

                    if (statusnew1.equals("Submitted")) {

                        status = "Submitted";
                        GetLeaveApplicationForApprove(status, flag);
                        ll_closeLL.setVisibility(View.VISIBLE);
                        cancelVisiblilty=true;


                    } else if (statusnew1.equals("Approved")) {
                        status = "Approved";
                        GetLeaveApplicationForApprove(status, flag);
                        ll_closeLL.setVisibility(View.INVISIBLE);
                        cancelVisiblilty=false;


                    } else if (statusnew1.equals("Rejected")) {
                        status = "Rejected";
                        GetLeaveApplicationForApprove(status, flag);
                        ll_closeLL.setVisibility(View.INVISIBLE);
                        cancelVisiblilty=false;

                    } else if (statusnew1.equals("Cancelled")) {

                        status = "Cancel";
                        GetLeaveApplicationForApprove(status, flag);
                        ll_closeLL.setVisibility(View.INVISIBLE);
                        cancelVisiblilty=false;

                    }

                check=true;



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
       // GetLeaveApplicationForApprove(status, flag);


        ll_new_application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent new_app_intent = new Intent(LeaveApplication.this, NewApplication.class);
                overridePendingTransition(R.anim.trans_left_in,
                        R.anim.trans_left_out);
                startActivity(new_app_intent);

            }
        });


        ll_application_rec_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // status = "Submitted";
                ll_tv_my_application_btn.setBackgroundColor(getResources().getColor(R.color.White));
                ll_application_rec_btn.setBackgroundColor(getResources().getColor(R.color.grey));
                flag = "List";
                GetLeaveApplicationForApprove(status, flag);
                tv_my_application.setTextSize(16);
                tv_application_rec.setTextSize(16);
                tv_application_rec.setTextColor(Color.parseColor("#2A5F3D"));
                tv_my_application.setTextColor(Color.parseColor("#000000"));
                value1="true";
                cancelVisiblilty=false;

                ll_application_rec.setVisibility(View.VISIBLE);
                ll_tv_my_application.setVisibility(View.GONE);
                ll_application.setVisibility(View.VISIBLE);
                ll_my_application.setVisibility(View.GONE);

            }
        });

        ll_tv_my_application_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
               // status = "Submitted";
                flag = "MyApplicationList";
                if (check==true){ GetLeaveApplicationForApprove(status, flag);}


                ll_tv_my_application_btn.setBackgroundColor(getResources().getColor(R.color.grey));
                ll_application_rec_btn.setBackgroundColor(getResources().getColor(R.color.White));
                tv_my_application.setTextSize(16);
                tv_application_rec.setTextSize(16);
                value1="false";
                tv_application_rec.setTextColor(Color.parseColor("#000000"));
                tv_my_application.setTextColor(Color.parseColor("#2A5F3D"));
                ll_application_rec.setVisibility(View.GONE);
                ll_tv_my_application.setVisibility(View.VISIBLE);
                ll_application.setVisibility(View.GONE);
                ll_my_application.setVisibility(View.VISIBLE);
                cancelVisiblilty=true;

            }
        });


        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.trans_right_in,
                        R.anim.trans_right_out);
            }
        });


        getCount();
    }

    private void GetLeaveApplicationForApprove(final String status1, final String flag) {

        pd = new ProgressDialog(LeaveApplication.this);
        pd.setMessage("Loading Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        RequestQueue sr2 = Volley.newRequestQueue(LeaveApplication.this);

        String url = Endpoints.GETLEAVEAPPFORAPPROVE;

        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("EmployeeId", AppPreferences.loadPreferences(LeaveApplication.this, "EmployeeId"));
        Log.e("EmployeeId", AppPreferences.loadPreferences(LeaveApplication.this, "EmployeeId"));
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(LeaveApplication.this, "CompanyCode"));

        Cid = AppPreferences.loadPreferences(LeaveApplication.this, "CompanyId");
        jsonParams.put("CompanyId", Cid);
        Log.e("CompanyId", Cid);

        jsonParams.put("SrNo", AppPreferences.loadPreferences(LeaveApplication.this, "SRNO"));
        Log.e("SrNo", AppPreferences.loadPreferences(LeaveApplication.this, "SRNO"));

        jsonParams.put("Flag", flag);
        Log.e("Flag", flag);

        jsonParams.put("Status", status1);
        Log.e("Status", status1);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        Log.e("GET_LEAVE_App" ,">>>>"+ response);
                        rl_success.setVisibility(View.VISIBLE);
                        rl_error.setVisibility(View.GONE);
                        System.out.println("====GET_LEAVE_App===" + response);
//                        pd.dismiss();

                        try {
//                            pd.dismiss();
                            pd.dismiss();
                            leaveApplicationBeans.clear();
                            String result = response.getString("result");
                            String Messages = response.getString("Messages");


                            if (result.equalsIgnoreCase("true")) {
//                                pd.dismiss();
                                JSONArray data = response.getJSONArray("data");
                                if (data.length() == 0) {
                                    pd.dismiss();
                                    // Toast.makeText(LeaveApplication.this, data.length() + "1>>>>", Toast.LENGTH_SHORT).show();
//                                    pd.dismiss();
                                    lv_my_app.setVisibility(View.GONE);
                                    lv_app_received.setVisibility(View.GONE);
                                    tv_lv_app_received.setVisibility(View.VISIBLE);
                                    tv_lv_my_app.setVisibility(View.VISIBLE);

                                } else {
                                  pd.dismiss();
                                    //  Toast.makeText(LeaveApplication.this, data.length() + "3>>>>", Toast.LENGTH_SHORT).show();

                                    lv_my_app.setVisibility(View.VISIBLE);
                                    lv_app_received.setVisibility(View.VISIBLE);
                                    tv_lv_app_received.setVisibility(View.GONE);
                                    tv_lv_my_app.setVisibility(View.GONE);
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject dataobj = data.getJSONObject(i);
//                                        pd.dismiss();

                                        String SNo = dataobj.getString("SNo");
                                        String CompanyId = dataobj.getString("CompanyId");
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
                                        String Statusnew = dataobj.getString("Status");
                                        String LeaveAppCount = dataobj.getString("LeaveAppCount");


//                                        pd.dismiss();
                                        leaveApplicationBeans.add(new leaveApplicationBean(SNo, CompanyId, EmployeeId, EmployeeName, Approver,
                                                LeaveType, FromDate, LeaveAppDate, FirstHalf, ToDate, SecondHalf, TimeFrom, TimeTo, Days, Remarks, Statusnew
                                                , LeaveAppCount));
                                    }

                                }
//                                pd.dismiss();
                                pd.dismiss();
                                leaveApplicationAdapter = new LeaveApplicationAdapter(LeaveApplication.this, leaveApplicationBeans);
                                lv_my_app.setAdapter(leaveApplicationAdapter);
                                leaveApplicationAdapter.notifyDataSetChanged();
                                lv_app_received.setAdapter(leaveApplicationAdapter);
                                leaveApplicationAdapter.notifyDataSetChanged();
                            } else {

//                                pd.dismiss();
                                pd.dismiss();
                            }


                        } catch (JSONException e) {
//                            pd.dismiss();
                            pd.dismiss();
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (null != error.networkResponse) {
//                            pd.dismiss();
                            //   pd.dismiss();
                            pd.dismiss();
                            rl_success.setVisibility(View.GONE);
                            rl_error.setVisibility(View.VISIBLE);
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

    private void getCount() {


//        pd2 = new ProgressDialog(LeaveApplication.this);
//        pd2.setMessage("Loading Please Wait...");
//        pd2.setCanceledOnTouchOutside(false);
//        pd2.show();

        RequestQueue sr1 = Volley.newRequestQueue(LeaveApplication.this);
        String url1 = Endpoints.GETLEAVEAPPCOUNT;

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("EmployeeId", AppPreferences.loadPreferences(LeaveApplication.this, "EmployeeId"));
        // jsonParams.put("CompanyId", AppPreferences.loadPreferences(LeaveApplication.this, "CompanyId"));
        if (Cid.equalsIgnoreCase("null")) {
            Cid = "0";
            jsonParams.put("CompanyId", Cid);
        } else
            {
            Cid = AppPreferences.loadPreferences(LeaveApplication.this, "CompanyId");
            jsonParams.put("CompanyId", Cid);
        }
        jsonParams.put("CompanyId", Cid);
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(LeaveApplication.this, "CompanyCode"));

        Log.e("jsonParams",jsonParams+"");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url1, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        System.out.println("====GET_LEAVE_COUNT===" + response);

                        try {
                            String result = response.getString("result");
                            String Messages = response.getString("Messages");
                            if (result.equalsIgnoreCase("true")) {
//                                pd2.dismiss();
                                String data = response.getString("data");

                                ll_count1.setVisibility(View.VISIBLE);
                                tv_count1.setText(data);

                            } else {
                                ll_count1.setVisibility(View.GONE);
                                tv_count1.setText("0");

//                                pd2.dismiss();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // pd2.dismiss();
                        if (null != error.networkResponse) {
                            // Toast.makeText(LeaveApplication.this, "Error", Toast.LENGTH_SHORT).show();
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

    private class LeaveApplicationAdapter extends BaseAdapter
    {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<leaveApplicationBean> leaveApplicationBeans;

        public LeaveApplicationAdapter(LeaveApplication leaveApplication, ArrayList<leaveApplicationBean> leaveApplicationBeans) {

            this.context = leaveApplication;
            inflater = LayoutInflater.from(context);
            this.leaveApplicationBeans = leaveApplicationBeans;
        }

        @Override
        public int getCount() {
            return leaveApplicationBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return leaveApplicationBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_type, tv_app_date,tv_days;
            LinearLayout ll_view, ll_close;

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.leave_app_item, parent, false);
                holder.tv_type = (TextView) view.findViewById(R.id.tv_type);
                holder.tv_app_date = (TextView) view.findViewById(R.id.tv_app_date);
                holder.ll_close = (LinearLayout) view.findViewById(R.id.ll_close);
                holder.ll_view = (LinearLayout) view.findViewById(R.id.ll_view);
                holder.tv_days=view.findViewById(R.id.tv_days);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            int total = position + 1;
            holder.tv_type.setText(leaveApplicationBeans.get(position).getLeaveType());
            holder.tv_days.setText(leaveApplicationBeans.get(position).getDays());
            String DATE_FORMAT_I = "yyyy-MM-dd'T'HH:mm:ss";
            String DATE_FORMAT_O = "dd-MM-yy";



             if (cancelVisiblilty)
             {
                 holder.ll_close.setVisibility(View.VISIBLE);
                 notifyDataSetChanged();
             }

             else
             {
                 holder.ll_close.setVisibility(View.INVISIBLE);
                 notifyDataSetChanged();
             }


            SimpleDateFormat formatInput = new SimpleDateFormat(DATE_FORMAT_I);
            SimpleDateFormat formatOutput = new SimpleDateFormat(DATE_FORMAT_O);

            Date date = null;
            try {
                date = formatInput.parse(leaveApplicationBeans.get(position).getLeaveAppDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String dateString = formatOutput.format(date);

            holder.tv_app_date.setText(dateString);
            holder.ll_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LeaveApplication.this, ViewLeave.class);
                    if (status.equalsIgnoreCase("Submitted"))
                    {
                    intent.putExtra("value","0");
                    intent.putExtra("value1",value1);
                    }
                    else if (status.equalsIgnoreCase("Approved"))
                    {intent.putExtra("value","2");}
                    else {intent.putExtra("value","1");}
                    AppPreferences.savePreferences(LeaveApplication.this, "SRNO", leaveApplicationBeans.get(position).getSNo());
                    startActivity(intent);

                }
            });



            holder.ll_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(LeaveApplication.this);
                    builder.setMessage("Are you sure to cancel");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteApi(leaveApplicationBeans.get(position).getSNo(), status, flag);

                            // onBackPressed();
                        }
                    });


        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                            dialogStatus = false;
            }
        });


                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                    //  Toast.makeText(LeaveApplication.this, ">>>>" + leaveApplicationBeans.get(position).getSNo(), Toast.LENGTH_SHORT).show();
                }
            });


            return view;
        }
    }


    private void deleteApi(String sNo, final String status1, final String flag1) {

        pd1 = new ProgressDialog(LeaveApplication.this);
        pd1.setMessage("Deleting...");
        pd1.setCanceledOnTouchOutside(false);
        pd1.show();


        String url =Endpoints.DELETELEAVEAPP;
        RequestQueue sr1 = Volley.newRequestQueue(LeaveApplication.this);

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("SrNo", sNo);
        jsonParams.put("CompanyId", AppPreferences.loadPreferences(LeaveApplication.this, "CompanyId"));
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(LeaveApplication.this, "CompanyCode"));


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("====Delete===" + response);

                        Log.e("Delete", ">>.>>>>"+ response);
                        pd1.dismiss();

                        try {
                            pd1.dismiss();
                            boolean result = response.getBoolean("result");
                            String Messages = response.getString("Messages");
                            int data = response.getInt("data");


                            if (result==true && Messages.equals("Successfull")) {


                                pd1.dismiss();

                                GetLeaveApplicationForApprove(status1, flag1);
//                                leaveApplicationAdapter = new LeaveApplicationAdapter(LeaveApplication.this, leaveApplicationBeans);
//                                lv_my_app.setAdapter(leaveApplicationAdapter);
//                                leaveApplicationAdapter.notifyDataSetChanged();
//                                lv_app_received.setAdapter(leaveApplicationAdapter);
//                                leaveApplicationAdapter.notifyDataSetChanged();

                            } else {
                                pd1.dismiss();
                                //  Toast.makeText(LeaveApplication.this, Messages, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            pd1.dismiss();
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd1.dismiss();
                        Log.e("Deleteerrror", ">>.>>>>"+ error);
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

//        overridePendingTransition(R.anim.trans_right_in,
//                R.anim.trans_right_out);
        Intent intent=new Intent(LeaveApplication.this,DashBord.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

}
