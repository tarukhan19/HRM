package com.hrm.hrm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.net.Uri;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.hrm.hrm.Bean.DeductRateAmount;
import com.hrm.hrm.Bean.DeductRateTitle;
import com.hrm.hrm.Bean.PayEarnAmount;
import com.hrm.hrm.Bean.PaySlipBean;
import com.hrm.hrm.Bean.PaySlipRateAmount;
import com.hrm.hrm.utils.AppPreferences;
import com.hrm.hrm.utils.Endpoints;
import com.hrm.hrm.utils.MonthAdapter;
import com.hrm.hrm.utils.SelectMonth;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaySlip extends Activity {
    // start
    String month_Id, month_Name, datamonthid;
    ArrayList<String> monthArrayList, monthArrayidList;
    ArrayAdapter<String> monthspinnerArrayAdapter;
    RequestQueue queue;
    Spinner spinnermonth;
    ArrayList<PaySlipBean> paySlipBeanArrayList;
    ArrayList<PaySlipRateAmount> paySlipRateAmountArrayList;
    ArrayList<PayEarnAmount> payEarnAmountArrayList;
    ArrayList<DeductRateAmount> deductRateAmountArrayList;
    ArrayList<DeductRateTitle> deductRateTitleArrayList;

    EarnTitleAdapter earnTitleAdapter;
    RateAmountAdapter rateAmountAdapter;
    EarnRateAdapter earnRateAdapter;
    DeductHeadAdapter deductHeadAdapter;
    DeductRateAdapter deductRateAdapter;

    // end
    ExpandableHeightListView earning_LT,rateamount_LT,earnamount_LT,deductamount_LT,deductHead_LT;

    LinearLayout ll_show, ll_back, ll_main;
    ProgressDialog pd;
    ScrollView sc_pay;
    String EmployeeId, GrossSalary, TotalDeduction, NetPay, BasicPay, DA, HRA, Bonus, Special,
            Medical, PaymentMode, BankAcNo, BankName, TDS, PF, e_cca, e_cea, month, monthid;

    LinearLayout ll_nodata;
//    TextView tv_BasicSalary_RA, tv_BasicSalary_EA, tv_hra_ra, tv_hra_ea, tv_ca_ra, tv_ca_ea,
//            tv_sa_ra, tv_sa_ea, tv_mr_ra, tv_mr_ea, tv_bonus_ra, tv_bonus_ea, tv_total_ra, tv_total_ea,
//            tv_pf, tv_total_deduction, tv_net_pay, tv_PaymentMode, tv_Bankname, tv_AccountNo;
    TextView tv_PaymentMode, tv_Bankname, tv_AccountNo,tv_net_pay,tv_total_deduction,tv_total_ea;
    String Cid;



//    WebView wv_pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_slip);


        ll_nodata = (LinearLayout) findViewById(R.id.ll_nodata);
        sc_pay = (ScrollView) findViewById(R.id.sc_pay);

        tv_total_ea = (TextView) findViewById(R.id.tv_total_ea);
//        tv_pf = (TextView) findViewById(R.id.tv_pf);
        tv_total_deduction = (TextView) findViewById(R.id.tv_total_deduction);
        tv_net_pay = (TextView) findViewById(R.id.tv_net_pay);
        tv_PaymentMode = (TextView) findViewById(R.id.tv_PaymentMode);
        tv_Bankname = (TextView) findViewById(R.id.tv_Bankname);
        tv_AccountNo = (TextView) findViewById(R.id.tv_AccountNo);
        earning_LT=findViewById(R.id.earning_LT);
        rateamount_LT=findViewById(R.id.rateamount_LT);
        earnamount_LT=findViewById(R.id.earnamount_LT);
        deductamount_LT=findViewById(R.id.deductamount_LT);
        deductHead_LT=findViewById(R.id.deductHead_LT);
        // start1 month
        queue = Volley.newRequestQueue(this);
        spinnermonth = (Spinner) findViewById(R.id.spinnermonth);

        monthArrayList = new ArrayList<>();
        monthArrayidList = new ArrayList<>();
        paySlipBeanArrayList= new ArrayList<>();
        paySlipRateAmountArrayList= new ArrayList<>();
        payEarnAmountArrayList= new ArrayList<>();
        deductRateAmountArrayList= new ArrayList<>();
        deductRateTitleArrayList= new ArrayList<>();

        earnTitleAdapter = new EarnTitleAdapter(PaySlip.this, paySlipBeanArrayList);
        rateAmountAdapter=new RateAmountAdapter(PaySlip.this, paySlipRateAmountArrayList);
        earnRateAdapter=new EarnRateAdapter(PaySlip.this, payEarnAmountArrayList);
        deductHeadAdapter=new DeductHeadAdapter(PaySlip.this, deductRateTitleArrayList);
        deductRateAdapter=new DeductRateAdapter(PaySlip.this, deductRateAmountArrayList);

        loadMonthSpinnerData();

        monthspinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, monthArrayList);
        monthspinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);






        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#A5C1EB"));

        }






        Cid = AppPreferences.loadPreferences(PaySlip.this, "CompanyId");

        String CompanyCode= AppPreferences.loadPreferences(PaySlip.this, "CompanyCode");

        Endpoints endpoints=new Endpoints(CompanyCode);


        ll_show = (LinearLayout) findViewById(R.id.ll_show);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.trans_right_in,
                        R.anim.trans_right_out);
            }
        });
        ll_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaySlipApi(monthid);
            }
        });

    }

    public void loadMonthSpinnerData()
    {
        pd = new ProgressDialog(PaySlip.this);
        pd.setMessage("Loading Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String APP_BASE_URL = Endpoints.PAY_MONTHLIST;
        RequestQueue sr2 = Volley.newRequestQueue(PaySlip.this);

        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(PaySlip.this, "CompanyCode"));
        jsonParams.put("CompanyId", AppPreferences.loadPreferences(PaySlip.this, "CompanyId"));



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, APP_BASE_URL, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        Log.e("response" ,">>>>"+ response);
                        try {

                            JSONObject obj = new JSONObject(String.valueOf(response));
                            boolean result = obj.getBoolean("result");
                            String messages = obj.getString("Messages");

                            if (result == true && messages.equalsIgnoreCase("Successfull"))
                            {

                                JSONArray monthjsonarray=obj.getJSONArray("data");

                                for(int i=0;i<monthjsonarray.length();i++)
                                {
                                    JSONObject jsonObject1=monthjsonarray.getJSONObject(i);
                                    month_Id=jsonObject1.getString("Id");
                                    month_Name=jsonObject1.getString("Name");

                                    monthArrayList.add(month_Name);
                                    monthArrayidList.add(month_Id);


                                }
                                spinnermonth.setAdapter(monthspinnerArrayAdapter);
                                selectSpinner();

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

                        }
                    }
                });
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        sr2.add(request);

    }

    private void selectSpinner() {

        spinnermonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                month=spinnermonth.getItemAtPosition(spinnermonth.getSelectedItemPosition()).toString();
                monthid=monthArrayidList.get(position).toString();
                //clienttypespinner.setSelection(clienttypeArrayList.indexOf(clienttypeIntent));}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    // start2

//    private void loadMonthSpinnerData(String APP_BASE_URL)
//    {
//        pd = new ProgressDialog(this);
//        pd.setMessage("Loading Please Wait...");
//        pd.setCanceledOnTouchOutside(false);
//        pd.show();
//
//        //@SuppressLint({"NewApi", "LocalSuppress"}) RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, APP_BASE_URL,
//                new Response.Listener<String>() {
//
//                    @SuppressLint("NewApi")
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            pd.dismiss();
//                            monthArrayidList.clear();
//                            monthArrayList.clear();
//                            JSONObject obj = new JSONObject(String.valueOf(response));
//                            boolean result = obj.getBoolean("result");
//                            String messages = obj.getString("Messages");
//
//                            if (result == true && messages.equals("Successfull"))
//                            {
//
//                                // String message = obj.getString("Messages");
//
//                                JSONArray monthjsonarray=obj.getJSONArray("data");
//                             //   Log.e("Array",""+monthjsonarray);
//
//                                for(int i=0;i<monthjsonarray.length();i++)
//                                {
//                                    JSONObject jsonObject1=monthjsonarray.getJSONObject(i);
//                                    month_Id=jsonObject1.getString("Id");
//                                    month_Name=jsonObject1.getString("Name");
//
//                                    monthArrayList.add(month_Name);
//                                    monthArrayidList.add(month_Id);
//
//
//                                }
//                            }
//                            spinnermonth.setAdapter(monthspinnerArrayAdapter);
//                        } catch (JSONException e) { e.printStackTrace(); }
//                    }
//
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }
//
//        ) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("CompanyId",AppPreferences.loadPreferences(PaySlip.this, "CompanyId"));
//                params.put("CompanyCode", AppPreferences.loadPreferences(PaySlip.this, "CompanyCode"));
//                Log.e("params", params.toString());
//                return params;
//            }
//        };
//
//        int socketTimeout = 30000;
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(policy);
//        queue.add(stringRequest);
//
//    }

    //end2

    private void PaySlipApi(String monthid)
    {

        pd = new ProgressDialog(PaySlip.this);
        pd.setMessage("Loading Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        RequestQueue sr1 = Volley.newRequestQueue(PaySlip.this);

        String url = Endpoints.EMPLOYEEPAYSLIP;

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("EmployeeId", AppPreferences.loadPreferences(PaySlip.this, "EmployeeId"));
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(PaySlip.this, "CompanyCode"));
        jsonParams.put("CompanyId", AppPreferences.loadPreferences(PaySlip.this, "CompanyId"));
        jsonParams.put("MonthId", monthid);

        Log.e("params","  "+jsonParams);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("====PaySlip===" , response+"");
                        pd.dismiss();

                        try {
                            paySlipRateAmountArrayList.clear();
                            paySlipBeanArrayList.clear();
                            payEarnAmountArrayList.clear();
                            deductRateTitleArrayList.clear();
                            deductRateAmountArrayList.clear();
                              pd.dismiss();
                              JSONObject obj = new JSONObject(String.valueOf(response));
//{"result":false,"Messages":"data not found.","data":null}
                            String result = obj.getString("result");
                            String Messages = obj.getString("Messages");

                                if (result.equalsIgnoreCase("true"))
                                {
                                    sc_pay.setVisibility(View.VISIBLE);
                                    JSONObject dataObj = obj.getJSONObject("data");
                                    JSONArray earningArray=dataObj.getJSONArray("earnings");
                                   // JSONArray RateArray=dataObj.getJSONArray("rate");
                                    JSONArray employeeArray=dataObj.getJSONArray("employee");
                                    JSONArray deductArray=dataObj.getJSONArray("deductions");

                                    JSONObject object=employeeArray.getJSONObject(0);


                                PaymentMode = object.getString("paymentmode");
                                BankAcNo = object.getString("bankacno");
                                BankName = object.getString("bankname");
                                NetPay = object.getString("netpay");
                                TotalDeduction = object.getString("totaldeduction");
                                GrossSalary=object.getString("grosssalary");


                                    for (int i=0;i<deductArray.length();i++)
                                    {
                                        JSONObject deductObj=deductArray.getJSONObject(i);
                                        DeductRateTitle deductRateTitle=new DeductRateTitle();
                                        DeductRateAmount deductRateAmount=new DeductRateAmount();

                                        String deductTitle=deductObj.getString("key");
                                        String deductAmount=deductObj.getString("value");

                                        if (!deductAmount.equalsIgnoreCase("0"))
                                        {
                                            deductRateTitle.setDeductTitle(deductTitle);
                                            deductRateTitleArrayList.add(deductRateTitle);

                                            deductRateAmount.setDeductAmount(deductAmount);
                                            deductRateAmountArrayList.add(deductRateAmount);
                                        }



                                    }
                                    deductHead_LT.setAdapter(deductHeadAdapter);
                                    deductHead_LT.setExpanded(true);
                                    deductHeadAdapter.notifyDataSetChanged();

                                    deductamount_LT.setAdapter(deductRateAdapter);
                                    deductamount_LT.setExpanded(true);
                                    deductRateAdapter.notifyDataSetChanged();





                                    for (int i=0;i<earningArray.length();i++)
                                {
                                    JSONObject earnObj=earningArray.getJSONObject(i);
                                    PaySlipBean paySlipBean=new PaySlipBean();
                                    PayEarnAmount paySlipearnAmount=new PayEarnAmount();
                                    PaySlipRateAmount paySlipRateAmount=new PaySlipRateAmount();
//
//
//


                                    String earnTitle=earnObj.getString("key");
                                    String earnamount=earnObj.getString("value");
                                    String rate=earnObj.getString("rate");

                                    if (!earnamount.equalsIgnoreCase("0")  || !rate.equalsIgnoreCase("0"))

                                    {
                                        paySlipBean.setEarnTitle(earnTitle);
                                        paySlipearnAmount.setEarnAmount(earnamount);
                                        paySlipRateAmount.setRateAmount(rate);

                                        paySlipBeanArrayList.add(paySlipBean);
                                        payEarnAmountArrayList.add(paySlipearnAmount);
                                        paySlipRateAmountArrayList.add(paySlipRateAmount);
                                    }


                                }
                                    earning_LT.setAdapter(earnTitleAdapter);
                                    earning_LT.setExpanded(true);
                                    earnTitleAdapter.notifyDataSetChanged();

                                    earnamount_LT.setAdapter(earnRateAdapter);
                                    earnamount_LT.setExpanded(true);
                                    earnRateAdapter.notifyDataSetChanged();

                                    rateamount_LT.setAdapter(rateAmountAdapter);
                                    rateamount_LT.setExpanded(true);
                                    rateAmountAdapter.notifyDataSetChanged();







//                                sc_pay.setVisibility(View.VISIBLE);
//                                ll_nodata.setVisibility(View.GONE);
//                                JSONObject data = response.getJSONObject("data");
//                                EmployeeId = data.getString("EmployeeId");
//                                GrossSalary = data.getString("GrossSalary");
//                                TotalDeduction = data.getString("TotalDeduction");
//                                NetPay = data.getString("NetPay");
//                                BasicPay = data.getString("BasicPay");
//                                DA = data.getString("DA");
//                                HRA = data.getString("HRA");
//                                Bonus = data.getString("Bonus");
//                                Special = data.getString("Special");
//                                Medical = data.getString("Medical");

//                                TDS = data.getString("TDS");
//                                PF = data.getString("PF");
//                                e_cca = data.getString("e_cca");
//                                e_cea = data.getString("e_cea");
//
////                                tv_BasicSalary_RA.setText("0");
////                                tv_BasicSalary_EA.setText(BasicPay);
////                                tv_hra_ra.setText("0");
////                                tv_hra_ea.setText(HRA);
////                                tv_ca_ra.setText("0");
////                                tv_ca_ea.setText(e_cea);
////                                tv_sa_ra.setText("0");
////                                tv_sa_ea.setText(Special);
////                                tv_mr_ra.setText("0");
////                                tv_mr_ea.setText(Medical);
////                                tv_bonus_ra.setText("0");
////                                tv_bonus_ea.setText(Bonus);
////                                tv_total_ra.setText("0");
//
//
////                                float totalamt = Float.parseFloat(BasicPay) + Float.parseFloat(HRA) + Float.parseFloat(e_cea) +
////                                        Float.parseFloat(Special)
////                                        + Float.parseFloat(Medical) + Float.parseFloat(Bonus);
////
////
////                                tv_pf.setText(PF);
////                                tv_total_deduction.setText(TDS);
////                                float netamt = totalamt + Float.parseFloat(PF) + Float.parseFloat(TDS);
////                                tv_net_pay.setText(String.valueOf(netamt));
////
////                                // Toast.makeText(PaySlip.this, ">>" + PaymentMode + ">>" + BankName + ">>" + BankAcNo, Toast.LENGTH_SHORT).show();
                                tv_PaymentMode.setText(PaymentMode);
                                tv_Bankname.setText(BankName);
                                tv_AccountNo.setText(BankAcNo);
                                tv_net_pay.setText(String.valueOf(NetPay));
                                    tv_total_deduction.setText(TotalDeduction);
                                    tv_total_ea.setText(GrossSalary);
//
////                                String googleDocsUrl = "http://docs.google.com/viewer?url=" + data;
////                                Intent intent = new Intent(Intent.ACTION_VIEW);
////                                intent.setDataAndType(Uri.parse(googleDocsUrl), "text/html");
////                                startActivity(intent);
//
//
////                                wv_pdf.getSettings().setJavaScriptEnabled(true);
//////                                String pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
////                                wv_pdf.loadUrl(data);
//
//                            } else {
//                                pd.dismiss();
//                                sc_pay.setVisibility(View.GONE);
//                                ll_nodata.setVisibility(View.VISIBLE);
//
//
                           }

                           else if (result.equalsIgnoreCase("false"))
                                {
                                    sc_pay.setVisibility(View.GONE);
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



    private class EarnTitleAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        private ArrayList<PaySlipBean> paySlipBeans;


        public EarnTitleAdapter(PaySlip newApplication, ArrayList<PaySlipBean> paySlipBeans) {

            this.context = newApplication;
            inflater = LayoutInflater.from(context);
            this.paySlipBeans = paySlipBeans;

        }

        @Override
        public int getCount() {
            return paySlipBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return paySlipBeans.get(position);
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
                view = inflater.inflate(R.layout.item_payslip, parent, false);
                holder.tv_leave = (TextView) view.findViewById(R.id.tv_leave);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            holder.tv_leave.setText(paySlipBeans.get(position).getEarnTitle());


            return view;
        }
    }






    private class RateAmountAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        ArrayList<PaySlipRateAmount> paySlipRateAmountArrayList;


        public RateAmountAdapter(PaySlip newApplication, ArrayList<PaySlipRateAmount> paySlipRateAmountArrayList) {

            this.context = newApplication;
            inflater = LayoutInflater.from(context);
            this.paySlipRateAmountArrayList = paySlipRateAmountArrayList;

        }

        @Override
        public int getCount() {
            return paySlipRateAmountArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return paySlipRateAmountArrayList.get(position);
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
                view = inflater.inflate(R.layout.item_payslip, parent, false);
                holder.tv_leave = (TextView) view.findViewById(R.id.tv_leave);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            holder.tv_leave.setText(paySlipRateAmountArrayList.get(position).getRateAmount());


            return view;
        }
    }





    private class EarnRateAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        ArrayList<PayEarnAmount> paySlipRateAmountArrayList;


        public EarnRateAdapter(PaySlip newApplication, ArrayList<PayEarnAmount> paySlipRateAmountArrayList) {

            this.context = newApplication;
            inflater = LayoutInflater.from(context);
            this.paySlipRateAmountArrayList = paySlipRateAmountArrayList;

        }

        @Override
        public int getCount() {
            return paySlipRateAmountArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return paySlipRateAmountArrayList.get(position);
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
                view = inflater.inflate(R.layout.item_payslip, parent, false);
                holder.tv_leave = (TextView) view.findViewById(R.id.tv_leave);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            holder.tv_leave.setText(paySlipRateAmountArrayList.get(position).getEarnAmount());


            return view;
        }
    }






    private class DeductRateAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        ArrayList<DeductRateAmount> deductRateAmountArrayList;


        public DeductRateAdapter(PaySlip newApplication, ArrayList<DeductRateAmount> deductRateAmountArrayList) {

            this.context = newApplication;
            inflater = LayoutInflater.from(context);
            this.deductRateAmountArrayList = deductRateAmountArrayList;

        }

        @Override
        public int getCount() {
            return deductRateAmountArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return deductRateAmountArrayList.get(position);
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
                view = inflater.inflate(R.layout.item_payslip, parent, false);
                holder.tv_leave = (TextView) view.findViewById(R.id.tv_leave);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            holder.tv_leave.setText(deductRateAmountArrayList.get(position).getDeductAmount());


            return view;
        }
    }





    private class DeductHeadAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        ArrayList<DeductRateTitle> deductRateTitleArrayList;


        public DeductHeadAdapter(PaySlip newApplication, ArrayList<DeductRateTitle> deductRateTitleArrayList) {

            this.context = newApplication;
            inflater = LayoutInflater.from(context);
            this.deductRateTitleArrayList = deductRateTitleArrayList;

        }

        @Override
        public int getCount() {
            return deductRateTitleArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return deductRateTitleArrayList.get(position);
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
                view = inflater.inflate(R.layout.item_payslip, parent, false);
                holder.tv_leave = (TextView) view.findViewById(R.id.tv_leave);

                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }
            holder.tv_leave.setText(deductRateTitleArrayList.get(position).getDeductTitle());


            return view;
        }
    }
}
