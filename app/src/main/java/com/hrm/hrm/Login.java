package com.hrm.hrm;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieSyncManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hrm.hrm.utils.AppConstants;
import com.hrm.hrm.utils.AppPreferences;
import com.hrm.hrm.utils.Endpoints;
import com.hrm.hrm.utils.GCMClientManager;
import com.hrm.hrm.utils.gcm_id;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Login extends Activity
{
    EditText edit_clientid, edit_Userid, edit_pass;
    LinearLayout ll_login, ll_forgot, ll_close1, ll_submit, ll_cancel;
    ProgressDialog pd, pd1;
    String clientid, userid, pass;
    public static final int PERMISSION_REQUEST = 100;

    ConnectivityManager cm;
    NetworkInfo activeNetwork;
    String answer;
    RequestQueue queue;
    String date, time1, time2;
    String datetime;
    String unique_id;
    String PROJECT_NUMBER = "724654024938";
    gcm_id gcm;
    String gcmId = "";
    GCMClientManager pushClientManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mayRequestPermissions();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#A5C1EB"));

        }


      //  mayRequestPermissions();

        queue = Volley.newRequestQueue(this);
        ll_login = (LinearLayout) findViewById(R.id.ll_login);
        ll_forgot = (LinearLayout) findViewById(R.id.ll_forgot);

        gcm = new gcm_id();
//        pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
//        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
//            @Override
//            public void onSuccess(String registrationId, boolean isNewRegistration) {
//
//                gcmId = registrationId;
//                Log.d("gcmId", gcmId);
//                gcm.setgcm(gcmId);
//
//
//            }
//
//            @Override
//            public void onFailure(String ex) {
//                super.onFailure(ex);
//            }
//        });




        edit_clientid = (EditText) findViewById(R.id.edit_clientid);
        edit_Userid = (EditText) findViewById(R.id.edit_Userid);
        edit_pass = (EditText) findViewById(R.id.edit_pass);





        edit_clientid.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                Endpoints endpoints=new Endpoints(edit_clientid.getText().toString());


                // TODO Auto-generated method stub

            }
        });

        ll_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (edit_clientid.getText().toString().trim().length() == 0) {
                    Toast toast = Toast.makeText(Login.this, "Please enter Client Id", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (edit_Userid.getText().toString().trim().length() == 0) {
                    Toast toast = Toast.makeText(Login.this, "Please enter User Id", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (edit_pass.getText().toString().trim().length() == 0) {
                    final Toast toast = Toast.makeText(getApplicationContext(),
                            "Please enter your password", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } else {

                    clientid = edit_clientid.getText().toString().trim();
                    userid = edit_Userid.getText().toString().trim();
                    pass = edit_pass.getText().toString().trim();



                   // Log.e("DATA", ">>>>" + clientid + "," + userid + "," + pass);
                    loginapp(clientid, userid, pass);

                }


            }

        });

        ll_forgot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Login.this, R.style.customDialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.forgot);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                ll_close1 = (LinearLayout) dialog.findViewById(R.id.ll_close1);
                ll_submit = (LinearLayout) dialog.findViewById(R.id.ll_submit);
                ll_cancel = (LinearLayout) dialog.findViewById(R.id.ll_cancel);

                final EditText edit_cmpnyid = (EditText) dialog.findViewById(R.id.edit_cmpnyid);
                final EditText edit_userid = (EditText) dialog.findViewById(R.id.edit_userid);
                final TextView edit_dob = (TextView) dialog.findViewById(R.id.edit_dob);
                edit_dob.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDateTimePicker(edit_dob);
                    }
                });


                ll_close1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                ll_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        hideSoftKeyboard();
                        //   Log.e("DOB",">>>>>" +edit_dob.getText());
//                        Toast.makeText(Login.this, edit_dob.getText(), Toast.LENGTH_SHORT).show();

                        if (edit_cmpnyid.getText().toString().trim().length() == 0) {
                            Toast toast = Toast.makeText(Login.this, "Please enter Company Id", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else if (edit_userid.getText().toString().trim().length() == 0) {
                            Toast toast = Toast.makeText(Login.this, "Please enter User Id", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else if (edit_dob.getText().toString().trim().length() == 0) {
                            final Toast toast = Toast.makeText(getApplicationContext(),
                                    "Please select your Date of Birth", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            //login_progress_bar.setVisibility(View.INVISIBLE);

                        } else if (edit_dob.getText().toString().trim().equals("null")) {

                            final Toast toast = Toast.makeText(getApplicationContext(),
                                    "Please select your Date of Birth", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else {

                            String cmpnyid = edit_cmpnyid.getText().toString().trim();
                            String userid = edit_userid.getText().toString().trim();

                            forgetPass(cmpnyid, userid, dialog);
                        }
                        //  forgetPass();
                    }
                });

                ll_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });




    }

    public void showDateTimePicker(final TextView tv_time) {
        final Calendar date1;

        final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
        final Calendar currentDate = Calendar.getInstance();
        date1 = Calendar.getInstance();
        new DatePickerDialog(Login.this, R.style.TimePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date1.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(Login.this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date1.set(Calendar.MINUTE, minute);
                        Log.e("DATE_TIME", "The choosen one " + date1.getTime());


                        datetime = String.valueOf(formate.format(date1.getTime()));
                        Log.e("NEW_DATE_TIME", "The choosen one " + datetime);

                        String[] separated = datetime.split(" ");
                        date = separated[0];
                        time1 = separated[1];
                        time2 = separated[2];
                        tv_time.setText(date);
                        //    tv_time.setText(getResources().getString(R.string.Time1) + " " + time1 + " " + time2);


                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    private void forgetPass(String cmpnyid1, String userid1, final Dialog dialog) {
        pd1 = new ProgressDialog(Login.this);
        pd1.setMessage("Loading Please Wait...");
        pd1.setCanceledOnTouchOutside(false);
        pd1.show();

        RequestQueue sr1 = Volley.newRequestQueue(Login.this);

        String url = Endpoints.FORGOTPASSWORD;

        Log.e("url",url);
            /*    {
                    "EmployeeId":"1",
                        "CompanyId":"ns",
                        "UserName":"101",
                        "Password":"101",
                        "DeviceId":"12345"
                }*/

        Map<String, Object> jsonParams = new ArrayMap<>();
//        jsonParams.put("EmployeeId", "1");
        jsonParams.put("UserName", userid1);
        jsonParams.put("CompanyCode", cmpnyid1);
        jsonParams.put("DOB", datetime);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        dialog.dismiss();
                        pd1.dismiss();
                        System.out.println("====ForgotPass===" + response);

                        try {
                            String result = response.getString("result");
                            String Messages = response.getString("Messages");
                            if (result.equalsIgnoreCase("true")) {
                                JSONObject data = response.getJSONObject("data");
                                Toast.makeText(Login.this, Messages, Toast.LENGTH_SHORT).show();
                            } else {


                                Toast.makeText(Login.this, Messages, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd1.dismiss();
                        if (null != error.networkResponse) {
                            Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void loginapp(final String c_id, final String u_id, final String cpass)
    {

        pd = new ProgressDialog(Login.this);
        pd.setMessage("Loading Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        String url = Endpoints.LOGIN;




        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("CompanyCode", c_id);
        jsonParams.put("UserName", u_id);
        jsonParams.put("Password", cpass);
        jsonParams.put("DeviceId", gcmId);

        RequestQueue sr1 = Volley.newRequestQueue(Login.this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("====LOGIN===" ,">>>>>>"+ response);
                        pd.dismiss();
                        try {
                            String result = response.getString("result");
                            String Messages = response.getString("Messages");
                            if (result.equalsIgnoreCase("true"))
                            {
                                JSONObject data = response.getJSONObject("data");
                                String EmployeeId = data.getString("EmployeeId");
                                String EmployeeCode = data.getString("EmployeeCode");
                                String CompanyId = data.getString("CompanyId");
                                String UserName = data.getString("UserName");
                                String Password = data.getString("Password");
                                String DeviceId = data.getString("DeviceId");
                                String UserRole = data.getString("UserRole");
                                String MonthId = data.getString("MonthId");
                                String Attributes = data.getString("Attributes");
                                String DOB = data.getString("DOB");
                                String SrNo = data.getString("SrNo");
                                String Flag = data.getString("Flag");
                                String Status = data.getString("Status");
                                String companycode=data.getString("CompanyCode");
                                String punchattendancestatus=data.getString("punchAttendance");
                                String leave_approver=data.getString("leave_approver");
                                String leave_module=data.getString("LeaveModule");
                                String register_locationstatus=data.getString("reg_location");

                                AppPreferences.savePreferences(Login.this, "CompanyCode", companycode);
                                AppPreferences.savePreferences(Login.this, "EmployeeId", EmployeeId);
                                AppPreferences.savePreferences(Login.this, "EmployeeCode", EmployeeCode);
                                AppPreferences.savePreferences(Login.this, "CompanyId", CompanyId);
                                AppPreferences.savePreferences(Login.this, "UserName", UserName);
                                AppPreferences.savePreferences(Login.this, "Password", Password);
                                AppPreferences.savePreferences(Login.this, "DeviceId", DeviceId);
                                AppPreferences.savePreferences(Login.this, "UserRole", UserRole);
                                AppPreferences.savePreferences(Login.this, "DOB", DOB);
//                                Toast.makeText(Login.this, EmployeeId, Toast.LENGTH_SHORT).show();
                                AppPreferences.savePreferences(Login.this, "loginStatus", "1");
                                AppPreferences.savePreferences(Login.this, "punchattendancestatus", punchattendancestatus);
                                AppPreferences.savePreferences(Login.this, "punchWithPhoto", "");
                                AppPreferences.savePreferences(Login.this, "leave_approver", leave_approver);
                                AppPreferences.savePreferences(Login.this, "register_locationstatus", register_locationstatus);

                                AppPreferences.savePreferences(Login.this, "LeaveModule", leave_module);

                                Intent intent = new Intent(Login.this, DashBord.class);
                                startActivity(intent);
                                System.out.println("====response===" + response);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
                                overridePendingTransition(R.anim.trans_left_in,
                                        R.anim.trans_left_out);
                                finish();
                                Log.e("LoginData", "" + response);
                            } else {


                                Toast.makeText(Login.this, Messages, Toast.LENGTH_SHORT).show();
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
                            Log.e("====response===" , error+"");
                        }
                    }


                });




        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        sr1.add(request);
    }

    private boolean mayRequestPermissions()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(CAMERA) + checkSelfPermission(READ_EXTERNAL_STORAGE) +
                checkSelfPermission(WRITE_EXTERNAL_STORAGE) + checkSelfPermission(CALL_PHONE) +
                checkSelfPermission(ACCESS_FINE_LOCATION) + checkSelfPermission(ACCESS_COARSE_LOCATION) +
                checkSelfPermission(READ_SMS) + checkSelfPermission(SEND_SMS) +
                checkSelfPermission(RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(R.string.permission_rationale);
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setMessage("Please confirm access to files & folders");
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{READ_CONTACTS, READ_EXTERNAL_STORAGE,
                                    WRITE_EXTERNAL_STORAGE, RECORD_AUDIO, CAMERA, RECEIVE_SMS, SEND_SMS,
                                    READ_PHONE_STATE, CALL_PHONE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},
                            PERMISSION_REQUEST);
                }
            });
            builder.show();
        } else {
            requestPermissions(new String[]{READ_CONTACTS, READ_EXTERNAL_STORAGE,
                            WRITE_EXTERNAL_STORAGE, RECORD_AUDIO, CAMERA, RECEIVE_SMS, SEND_SMS,
                            READ_PHONE_STATE, CALL_PHONE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST);
        }
        return false;
    }

}
