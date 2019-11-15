package com.hrm.hrm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.util.ArrayMap;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hrm.hrm.Bean.AchievementsBean;
import com.hrm.hrm.Bean.BirthdayBean;
import com.hrm.hrm.Bean.DocBean;
import com.hrm.hrm.Bean.FamailyMembersBean;
import com.hrm.hrm.Bean.HobbiesBean;
import com.hrm.hrm.Bean.QualificationBean;
import com.hrm.hrm.Bean.SkillsBean;
import com.hrm.hrm.Bean.WorkingBean;
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

public class Birthday extends Activity {


    LinearLayout ll_back;
    ProgressDialog pd;
    ListView lv_Birthday;
    ArrayList<BirthdayBean> birthdayBeans;
    BirthdayAdapter birthdayAdapter;
    TextView tv_no_data;

    String Cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#A5C1EB"));

        }
        Cid = AppPreferences.loadPreferences(Birthday.this, "CompanyId");
        String CompanyCode= AppPreferences.loadPreferences(Birthday.this, "CompanyCode");

        Endpoints endpoints=new Endpoints(CompanyCode);


        birthdayBeans = new ArrayList<>();
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        lv_Birthday = (ListView) findViewById(R.id.lv_Birthday);

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.trans_left_in,
                        R.anim.trans_left_out);
            }
        });

        Birthdayapi();

    }

    private void Birthdayapi()
    {

        pd = new ProgressDialog(Birthday.this);
        pd.setMessage("Loading Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        RequestQueue sr1 = Volley.newRequestQueue(Birthday.this);


//        String url = "http://103.205.65.52/MobileApp/GetDashbordAttriApi";

        String url = Endpoints.DASHBOARDATTRIBUTE;

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("Attributes", "Birthday");
        if (Cid.equalsIgnoreCase("null")) {
            Cid = "0";
            jsonParams.put("CompanyId", Cid);
        } else {
            Cid = AppPreferences.loadPreferences(Birthday.this, "CompanyId");
            jsonParams.put("CompanyId", Cid);
        }
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(Birthday.this, "CompanyCode"));
        jsonParams.put("EmployeeId",  AppPreferences.loadPreferences(Birthday.this, "EmployeeId"));

        //  jsonParams.put("CompanyId", AppPreferences.loadPreferences(Birthday.this, "CompanyId"));

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("====response===" + response);
                        pd.dismiss();

                        try {
                            pd.dismiss();
                            String result = response.getString("result");
                            String Messages = response.getString("Messages");


                            if (result.equalsIgnoreCase("true")) {
                                lv_Birthday.setVisibility(View.VISIBLE);
                                tv_no_data.setVisibility(View.GONE);
                                JSONArray data = response.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject dataobj = data.getJSONObject(i);
                                    String EmployeeId = dataobj.getString("EmployeeId");
                                    String EmployeeCode = dataobj.getString("EmployeeCode");
                                    String Name = dataobj.getString("Name");
                                    String BirthDate = dataobj.getString("BirthDate");
                                    String DaysToGo = dataobj.getString("DaysToGo");


                                    Log.e("BirthDay_DATA", ">>>" + EmployeeId + EmployeeCode + Name);

                                    birthdayBeans.add(new BirthdayBean(EmployeeId, EmployeeCode, Name, BirthDate, DaysToGo));
                                }


                                birthdayAdapter = new BirthdayAdapter(Birthday.this, birthdayBeans);
                                lv_Birthday.setAdapter(birthdayAdapter);
                                birthdayAdapter.notifyDataSetChanged();
                            }else {


                                lv_Birthday.setVisibility(View.GONE);
                                tv_no_data.setVisibility(View.VISIBLE);
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
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);
    }

    private class BirthdayAdapter extends BaseAdapter {

        Context context;
        private LayoutInflater inflater;
        private ArrayList<BirthdayBean> birthdayBeans;

        public BirthdayAdapter(Birthday birthday, ArrayList<BirthdayBean> birthdayBeans) {

            this.context = birthday;
            inflater = LayoutInflater.from(context);
            this.birthdayBeans = birthdayBeans;
        }

        @Override
        public int getCount() {
            return birthdayBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return birthdayBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_h_Remark, tv_h_Name, tv_serial;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.birthday_list, parent, false);
                holder.tv_h_Name = (TextView) view.findViewById(R.id.tv_h_Name);
                holder.tv_h_Remark = (TextView) view.findViewById(R.id.tv_h_Remark);
                holder.tv_serial = (TextView) view.findViewById(R.id.tv_serial);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            int total = position + 1;
            holder.tv_serial.setText(birthdayBeans.get(position).getName());

            String DATE_FORMAT_I = "yyyy-MM-dd'T'HH:mm:ss";
            String DATE_FORMAT_O = "yyyy-MM-dd";


            SimpleDateFormat formatInput = new SimpleDateFormat(DATE_FORMAT_I);
            SimpleDateFormat formatOutput = new SimpleDateFormat(DATE_FORMAT_O);

            Date date = null;
            try {
                date = formatInput.parse(birthdayBeans.get(position).getBirthDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String dateString = formatOutput.format(date);
            holder.tv_h_Name.setText(dateString);
            holder.tv_h_Remark.setText(birthdayBeans.get(position).getDaysToGo());


            return view;
        }
    }
}
