package com.hrm.hrm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import com.hrm.hrm.Bean.GuidelinesBean;
import com.hrm.hrm.Bean.NewsLetterBean;
import com.hrm.hrm.utils.AppPreferences;
import com.hrm.hrm.utils.Endpoints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class Newsletter extends Activity {
    LinearLayout ll_back;
    ListView lv_newsletter;
    ArrayList<NewsLetterBean> newsLetterBeans;
    ProgressDialog pd;
    NewsLetterAdapter newsLetterAdapter;
    TextView tv_no_data;
    String Cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsletter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#A5C1EB"));

        }

        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        lv_newsletter = (ListView) findViewById(R.id.lv_newsletter);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.trans_right_in,
                        R.anim.trans_right_out);
            }
        });
        Cid = AppPreferences.loadPreferences(Newsletter.this, "CompanyId");
        String CompanyCode= AppPreferences.loadPreferences(Newsletter.this, "CompanyCode");

        Endpoints endpoints=new Endpoints(CompanyCode);

        newsLetterBeans = new ArrayList<>();

        newsletterApi();
    }

    private void newsletterApi() {

        pd = new ProgressDialog(Newsletter.this);
        pd.setMessage("Loading Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        RequestQueue sr1 = Volley.newRequestQueue(Newsletter.this);

       // String url = "http://103.205.65.52/MobileApp/GetDashbordAttriApi";
        String url = Endpoints.DASHBOARDATTRIBUTE;


        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("Attributes", "Newsletters");
       // jsonParams.put("CompanyId", AppPreferences.loadPreferences(Newsletter.this, "CompanyId"));
        if (Cid.equalsIgnoreCase("null")) {
            Cid = "0";
            jsonParams.put("CompanyId", Cid);
        } else {
            Cid = AppPreferences.loadPreferences(Newsletter.this, "CompanyId");
            jsonParams.put("CompanyId", Cid);
            jsonParams.put("CompanyCode", AppPreferences.loadPreferences(Newsletter.this, "CompanyCode"));
            jsonParams.put("EmployeeId",  AppPreferences.loadPreferences(Newsletter.this, "EmployeeId"));

        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("====Newsletters===" + response);
                        pd.dismiss();

                        try {
                            pd.dismiss();
                            String result = response.getString("result");
                            String Messages = response.getString("Messages");


                            if (result.equalsIgnoreCase("true")) {

                                lv_newsletter.setVisibility(View.VISIBLE);
                                tv_no_data.setVisibility(View.GONE);
                                JSONArray data = response.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject dataobj = data.getJSONObject(i);
                                    String GId = dataobj.getString("GId");
                                    String subject = dataobj.getString("subject");
                                    String file_name = dataobj.getString("file_name");


                                    newsLetterBeans.add(new NewsLetterBean(GId, subject, file_name));
                                }


                                newsLetterAdapter = new NewsLetterAdapter(Newsletter.this, newsLetterBeans);
                                lv_newsletter.setAdapter(newsLetterAdapter);
                                newsLetterAdapter.notifyDataSetChanged();
                            }else {

                                lv_newsletter.setVisibility(View.GONE);
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

    private class NewsLetterAdapter extends BaseAdapter {


        Context context;
        private LayoutInflater inflater;
        private ArrayList<NewsLetterBean> newsLetterBeans;


        public NewsLetterAdapter(Newsletter newsletter, ArrayList<NewsLetterBean> newsLetterBeans) {

            this.context = newsletter;
            inflater = LayoutInflater.from(context);
            this.newsLetterBeans = newsLetterBeans;


        }

        @Override
        public int getCount() {
            return newsLetterBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return newsLetterBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public class Holder {

            TextView tv_g_serial, tv_g_Name;
            LinearLayout ll_doc_image;

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.guideline_list, parent, false);
                holder.tv_g_serial = (TextView) view.findViewById(R.id.tv_g_serial);
                holder.tv_g_Name = (TextView) view.findViewById(R.id.tv_g_Name);
                holder.ll_doc_image = (LinearLayout) view.findViewById(R.id.ll_doc_image);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            int total = position + 1;
            holder.tv_g_serial.setText(String.valueOf(total));
            AppPreferences.savePreferences(Newsletter.this, "File", newsLetterBeans.get(position).getFile_name());
            holder.ll_doc_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String googleDocsUrl = "http://docs.google.com/viewer?url=" + newsLetterBeans.get(position).getFile_name();
                   // googleDocsUrl=googleDocsUrl.replace(" ","%20");
                    Log.e("googleDocsUrl",googleDocsUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(googleDocsUrl), "text/html");
                    startActivity(intent);
                }
            });
            holder.tv_g_Name.setText(newsLetterBeans.get(position).getSubject());


            return view;
        }
    }
}
