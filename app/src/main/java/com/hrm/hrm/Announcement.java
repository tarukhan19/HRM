package com.hrm.hrm;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.hrm.hrm.Bean.AnnouncementBean;
import com.hrm.hrm.Bean.NewsBean;
import com.hrm.hrm.utils.AppPreferences;
import com.hrm.hrm.utils.Endpoints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class Announcement extends AppCompatActivity {

    LinearLayout ll_back;
    ListView lv_announcements;
    ProgressDialog pd;
    TextView tv_nodata;
    AnnouncementAdapter announcementAdapter;
    String Cid;
    ArrayList<AnnouncementBean> announcementBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#A5C1EB"));

        }

        Cid = AppPreferences.loadPreferences(Announcement.this, "CompanyId");
        String CompanyCode= AppPreferences.loadPreferences(Announcement.this, "CompanyCode");

        Endpoints endpoints=new Endpoints(CompanyCode);


        tv_nodata = (TextView) findViewById(R.id.tv_nodata);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.trans_right_in,
                        R.anim.trans_right_out);
            }
        });
        announcementBean = new ArrayList<>();
        lv_announcements = (ListView) findViewById(R.id.lv_announcements);

        announcementsApi();
    }

    private void announcementsApi() {

        pd = new ProgressDialog(Announcement.this);
        pd.setMessage("Loading Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        RequestQueue sr1 = Volley.newRequestQueue(Announcement.this);
        String url = Endpoints.DASHBOARDATTRIBUTE;


        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("Attributes", "Announcements");
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(Announcement.this, "CompanyCode"));
        jsonParams.put("EmployeeId",  AppPreferences.loadPreferences(Announcement.this, "EmployeeId"));

        // jsonParams.put("CompanyId", AppPreferences.loadPreferences(Announcement.this, "CompanyId"));
        if (Cid.equalsIgnoreCase("null")) {
            Cid = "0";
            jsonParams.put("CompanyId", Cid);
        } else {
            Cid = AppPreferences.loadPreferences(Announcement.this, "CompanyId");
            jsonParams.put("CompanyId", Cid);
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("====Announcements===" + response);
                        pd.dismiss();

                        try {
                            pd.dismiss();
                            String result = response.getString("result");
                            String Messages = response.getString("Messages");


                            if (result.equalsIgnoreCase("true")) {

                                lv_announcements.setVisibility(View.VISIBLE);
                                tv_nodata.setVisibility(View.GONE);
                                JSONArray data = response.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject dataobj = data.getJSONObject(i);
                                    String AnnId = dataobj.getString("AnnId");
                                    String Subject = dataobj.getString("Subject");
                                    String description = dataobj.getString("description");
                                    String AnnDate = dataobj.getString("AnnDate");


                                    announcementBean.add(new AnnouncementBean(AnnId, Subject, description, AnnDate));
                                }


                                announcementAdapter = new AnnouncementAdapter(Announcement.this, announcementBean);
                                lv_announcements.setAdapter(announcementAdapter);
                                announcementAdapter.notifyDataSetChanged();
                            } else if (result.equalsIgnoreCase("false")) {


                                lv_announcements.setVisibility(View.GONE);
                                tv_nodata.setVisibility(View.VISIBLE);
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

    private class AnnouncementAdapter extends BaseAdapter {

        Context context;
        private LayoutInflater inflater;
        private ArrayList<AnnouncementBean> announcementBeans;

        public AnnouncementAdapter(Announcement announcement, ArrayList<AnnouncementBean> announcementBean) {
            this.context = announcement;
            inflater = LayoutInflater.from(context);
            this.announcementBeans = announcementBean;

        }

        @Override
        public int getCount() {
            return announcementBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return announcementBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_g_serial, tv_g_Name;
            Button btn_readmore;

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.news_list, parent, false);
                holder.tv_g_serial = (TextView) view.findViewById(R.id.tv_g_serial);
                holder.tv_g_Name = (TextView) view.findViewById(R.id.tv_g_Name);
                holder.btn_readmore = (Button) view.findViewById(R.id.btn_readmore);
                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
//            int total = position + 1;
//            holder.tv_g_serial.setText(String.valueOf(total));

            holder.tv_g_serial.setText(announcementBeans.get(position).getSubject());
            holder.tv_g_Name.setText(announcementBeans.get(position).getAnnDate());
            holder.btn_readmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(Announcement.this, R.style.customDialog);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.show_image1);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    LinearLayout ll_close = (LinearLayout) dialog.findViewById(R.id.ll_close);
                    ll_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    TextView tv_news = (TextView) dialog.findViewById(R.id.tv_news);
                    tv_news.setText(announcementBean.get(position).getDescription());
                    dialog.show();

                }
            });


            return view;
        }
    }
}
