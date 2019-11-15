package com.hrm.hrm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hrm.hrm.Bean.BirthdayBean;
import com.hrm.hrm.Bean.HolidayBean;
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

public class Holiday extends Activity {

    LinearLayout ll_back;
    ArrayList<HolidayBean> holidayBeans;
    HolidayAdapter holidayAdapter;
    ListView lv_holiday;
    ProgressDialog pd;
    TextView tv_no_data;
    SimpleDateFormat formatInput, formatOutput;
    Date datedob = null;
    Date datedoc = null;
    String DATE_FORMAT_I, DATE_FORMAT_O;
    String Cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#A5C1EB"));

        }

        holidayBeans = new ArrayList<>();
        DATE_FORMAT_I = "yyyy-MM-dd'T'HH:mm:ss";
        DATE_FORMAT_O = "dd MMMM";
        Cid = AppPreferences.loadPreferences(Holiday.this, "CompanyId");


        formatInput = new SimpleDateFormat(DATE_FORMAT_I);
        formatOutput = new SimpleDateFormat(DATE_FORMAT_O);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        lv_holiday = (ListView) findViewById(R.id.lv_holiday);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.trans_right_in,
                        R.anim.trans_right_out);
            }
        });



        String CompanyCode= AppPreferences.loadPreferences(Holiday.this, "CompanyCode");

        Endpoints endpoints=new Endpoints(CompanyCode);



        holidayApi();
    }

    private void holidayApi() {

        pd = new ProgressDialog(Holiday.this);
        pd.setMessage("Loading Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        RequestQueue sr1 = Volley.newRequestQueue(Holiday.this);


        String url = Endpoints.DASHBOARDATTRIBUTE;


        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("Attributes", "Holidays");
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(Holiday.this, "CompanyCode"));
        jsonParams.put("EmployeeId",  AppPreferences.loadPreferences(Holiday.this, "EmployeeId"));

        //jsonParams.put("CompanyId", AppPreferences.loadPreferences(Holiday.this, "CompanyId"));
        if (Cid.equalsIgnoreCase("null")) {
            Cid = "0";
            jsonParams.put("CompanyId", Cid);
        } else {
            Cid = AppPreferences.loadPreferences(Holiday.this, "CompanyId");
            jsonParams.put("CompanyId", Cid);
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("====Holidays===" + response);
                        pd.dismiss();

                        try {
                            pd.dismiss();
                            String result = response.getString("result");
                            String Messages = response.getString("Messages");


                            if (result.equalsIgnoreCase("true")) {
                                lv_holiday.setVisibility(View.VISIBLE);
                                tv_no_data.setVisibility(View.GONE);

                                JSONArray data = response.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject dataobj = data.getJSONObject(i);
                                    String HId = dataobj.getString("HId");
                                    String HDate = dataobj.getString("HDate");
                                    String HolidayDesc = dataobj.getString("HolidayDesc");


                                    holidayBeans.add(new HolidayBean(HId, HDate, HolidayDesc));
                                }


                                holidayAdapter = new HolidayAdapter(Holiday.this, holidayBeans);
                                lv_holiday.setAdapter(holidayAdapter);
                                holidayAdapter.notifyDataSetChanged();
                            }else {


                                lv_holiday.setVisibility(View.GONE);
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

    private class HolidayAdapter extends BaseAdapter {

        Context context;
        private LayoutInflater inflater;
        private ArrayList<HolidayBean> holidayBeans;


        public HolidayAdapter(Holiday holiday, ArrayList<HolidayBean> holidayBeans) {

            this.context = holiday;
            inflater = LayoutInflater.from(context);
            this.holidayBeans = holidayBeans;

        }

        @Override
        public int getCount() {
            return holidayBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return holidayBeans.get(position);
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
                view = inflater.inflate(R.layout.holiday_list, parent, false);
                holder.tv_h_Name = (TextView) view.findViewById(R.id.tv_h_Name);
                holder.tv_h_Remark = (TextView) view.findViewById(R.id.tv_h_Remark);
                holder.tv_serial = (TextView) view.findViewById(R.id.tv_serial);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            int total = position + 1;
            holder.tv_serial.setText(String.valueOf(total));

            holder.tv_h_Remark.setText(holidayBeans.get(position).getHolidayDesc());

            try {
                datedob = formatInput.parse(holidayBeans.get(position).gethDate());

            } catch (ParseException e) {
                e.printStackTrace();
            }
            String dateStringDOB = formatOutput.format(datedob);
            holder.tv_h_Name.setText(dateStringDOB);


            return view;
        }
    }
}
