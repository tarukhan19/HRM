package com.hrm.hrm;

import android.*;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieSyncManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.hrm.hrm.LocationUtil.PermissionUtils;
import com.hrm.hrm.utils.AppPreferences;
import com.hrm.hrm.utils.Endpoints;
import com.hrm.hrm.utils.MarshMallowPermission;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class DashBord extends AppCompatActivity  {
    String[] menu, menucount;
    DrawerLayout dLayout;
    Intent intent;
    ListView dList;
    ArrayAdapter<String> adapter;
    AQuery aq;
    RequestQueue queue;
    ProgressDialog pd;
    String profile ;
    CircleImageView imageView2;
    String date, time1, time2,punchattendancestatus,punchWithPhoto,register_locationstatus;
    ImageView img2;
    private int img_height, img_width;
    public static final String PREFS_NAME = "MyPrefs";
    ArrayList<String> dashBeen1;
    DrawerAdapter drawerAdapter;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String picturePath = "";
    TextView tv_name, tv_age, tv_branch, tv_dept, tv_designation;
    LinearLayout ll_punch, ll_profile, ll_holiday, payslip, ll_leaveapp, ll_dailyAtten,ll_reglocation;
    ScrollView sc_profile;
    boolean statusOfGPS;
    private Boolean mRequestingLocationUpdates;
    String leaveModule;
    private final int CAMERA_PIC = 02;
    Bitmap picBitmap;
    Uri imageUri;
    byte[] profilePicbyte= null;
    public static final int PERMISSION_REQUEST = 100;
    // boolean flag to toggle the ui

    String EmpId, EmpCode, Name, Gender, DOB, DOJ, DOC,
            FatherName, SpouseName, Photo, PFApply, PFAppleDate, PFNo, PFUAN, ESIC_Application, ESIC_No,
            PAN, Aadhar, PTApplication, BranchName,
            Grade, Department, Designation, Division, CostCenter, Project, Category, PaymentMode,
            BankName, AccountNo, IfscCode, AccountName, DOL, Address1, Address2, City, State, Country,
            PinCode, Address1Per, Address2Per, CityPer, StatePer, CountryPer, PinCodePer, Telephone, MartialStatus,
            PersonalMobileNo, MarriageDate, OfficialMobileNo, NoOfChildren, Religion, EmailPersonal, BloodGroup, EmailOfficial,
            Height, ESICDispensary, EmergencyContactName, Spectacles, EmergencyContactNo,
            PassportNo, PassportIssueDate, PassportExpiryDate, PassportPlaceOfIssue, VehicleDetails, DrivingLicenseNo,
            DrivingLicenseIssueDate, DrivingLicenseExpiryDate, TwoWheeler, FourWheeler, DrugLicenseNo, DrugIssueDate,
            DrugExpiryDate, MedicalPolicies, BirthDayCount, HolidaysCount, AnnouncementsCount, NewsCount, NewsLettersCount, GuidelinesCount;

    String BirthDay, Holidays, Announcements, News, NewsLetters, Guidelines;

    MarshMallowPermission marshMallowPermission;
    String Cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_bord);
        intent=getIntent();
        pd = new ProgressDialog(DashBord.this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#A5C1EB"));

        }
        Cid = AppPreferences.loadPreferences(DashBord.this, "CompanyId");
        leaveModule=AppPreferences.loadPreferences(DashBord.this,"LeaveModule");
        punchattendancestatus=AppPreferences.loadPreferences(DashBord.this,"punchattendancestatus");
        register_locationstatus=AppPreferences.loadPreferences(DashBord.this,"register_locationstatus");
        punchWithPhoto=AppPreferences.loadPreferences(DashBord.this,"punchWithPhoto");
        aq = new AQuery(DashBord.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String CompanyCode= AppPreferences.loadPreferences(DashBord.this, "CompanyCode");

        Endpoints endpoints=new Endpoints(CompanyCode);

        profile = Endpoints.GETMYPROFILE;
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_branch = (TextView) findViewById(R.id.tv_branch);
        tv_dept = (TextView) findViewById(R.id.tv_dept);
        tv_designation = (TextView) findViewById(R.id.tv_designation);

        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.drawerheader, null, false);

        ll_punch = (LinearLayout) findViewById(R.id.ll_punch);
        ll_profile = (LinearLayout) findViewById(R.id.ll_profile);
        ll_holiday = (LinearLayout) findViewById(R.id.ll_holiday);
        payslip = (LinearLayout) findViewById(R.id.payslip);
        ll_leaveapp = (LinearLayout) findViewById(R.id.ll_leaveapp);
        ll_dailyAtten = (LinearLayout) findViewById(R.id.ll_dailyAtten);
        ll_reglocation=findViewById(R.id.ll_reglocation);
        sc_profile = (ScrollView) findViewById(R.id.sc_profile);
        imageView2 = (CircleImageView) findViewById(R.id.imageView2);
        img2 = (ImageView) findViewById(R.id.img2);

        if (punchattendancestatus.equalsIgnoreCase("false"))
        {
            ll_punch.setVisibility(View.GONE);
        }
        else
        {
            ll_punch.setVisibility(View.VISIBLE);
        }

        if (register_locationstatus.equalsIgnoreCase("false"))
        {
            ll_reglocation.setVisibility(View.GONE);
        }
        else
        {
            ll_reglocation.setVisibility(View.VISIBLE);

        }



        getProfile();

        ll_leaveapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent leave_intent = new Intent(DashBord.this, LeaveApplication.class);
                overridePendingTransition(R.anim.trans_left_in,
                        R.anim.trans_left_out);
                startActivity(leave_intent);
            }
        });
        payslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent birth_intent = new Intent(DashBord.this, PaySlip.class);
                overridePendingTransition(R.anim.trans_left_in,
                        R.anim.trans_left_out);
                startActivity(birth_intent);
            }
        });

        ll_punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent=new Intent(DashBord.this,PunchAttendance.class);
                    intent.putExtra("from","punch");
                    startActivity(intent);


            }
        });


        ll_reglocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(DashBord.this,PunchAttendance.class);
                intent.putExtra("from","locationdes");
                startActivity(intent);


            }
        });

        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashBord.this, Nav_List.class);
                intent.putExtra("Menu", "My Profile");
                // getText() SHOULD NOT be static!!!
                overridePendingTransition(R.anim.trans_left_in,
                        R.anim.trans_left_out);
                startActivity(intent);
            }
        });

        ll_holiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent birth_intent = new Intent(DashBord.this, Holiday.class);
                overridePendingTransition(R.anim.trans_left_in,
                        R.anim.trans_left_out);
                startActivity(birth_intent);
            }
        });
        payslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent birth_intent = new Intent(DashBord.this, PaySlip.class);
                overridePendingTransition(R.anim.trans_left_in,
                        R.anim.trans_left_out);
                startActivity(birth_intent);
            }
        });


        ll_dailyAtten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cal_intent = new Intent(DashBord.this, CalenderView.class);
                overridePendingTransition(R.anim.trans_left_in,
                        R.anim.trans_left_out);
                startActivity(cal_intent);
            }
        });

        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, dLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {

                InputMethodManager inputMethodManager = (InputMethodManager) DashBord.this
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
               try
               {
                   inputMethodManager.hideSoftInputFromWindow(
                           DashBord.this.getCurrentFocus().getWindowToken(), 0);
               }
               catch (Exception e)
               {}




                super.onDrawerClosed(drawerView);
            }

            Integer[] imageId = {
                    R.mipmap.profile


            };

            @Override
            public void onDrawerClosed(View drawerView) {


                InputMethodManager inputMethodManager = (InputMethodManager) DashBord.this
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
               try{
                   inputMethodManager.hideSoftInputFromWindow(
                           DashBord.this.getCurrentFocus().getWindowToken(),
                           0
                   );
               }
               catch (Exception e)
               {}

                super.onDrawerOpened(drawerView);
            }
        };
        dLayout.setDrawerListener(toggle);
        toggle.syncState();
        dList = (ListView) findViewById(R.id.left_drawer);
        dList.addHeaderView(listHeaderView, null, false);

        dashBeen1 = new ArrayList<>();


        dashBeen1.add("My Profile");
        dashBeen1.add("Birthday");
        dashBeen1.add("Holiday");
        dashBeen1.add("Guidelines");
        dashBeen1.add("Announcement");
        dashBeen1.add("News");
        dashBeen1.add("NewsLetter");
        dashBeen1.add("PaySlip");

        if (leaveModule.equalsIgnoreCase("true"))
        {
            dashBeen1.add("Leave Application");
            ll_leaveapp.setVisibility(View.VISIBLE);
        }

        else
        {
            ll_leaveapp.setVisibility(View.GONE);

        }
     //
        dashBeen1.add("Daily Attendance");
        dashBeen1.add("Logout");
        //  menu = new String[]{"My Profile", "Birthday", "Holiday", "Guidelines", "Announcement", "News",
        // "NewsLetter", "PaySlip", "Leave Application", "Calender", "Logout"};

        drawerAdapter = new DrawerAdapter(DashBord.this, dashBeen1);

        dList.setAdapter(drawerAdapter);

        dList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {


                dLayout.closeDrawers();

                //My Profile", "Birthday", "Holiday", "Announcement",
                // "News", "NewsLetter", "Guidelines", "PaySlip",
                // "Leave Application", "Calender", "Logout"
                if (dashBeen1.get(position - 1).equalsIgnoreCase("My Profile")) {
                    Intent intent = new Intent(DashBord.this, Nav_List.class);
                    intent.putExtra("Menu", dashBeen1.get(position - 1));
                    // getText() SHOULD NOT be static!!!
                    overridePendingTransition(R.anim.trans_left_in,
                            R.anim.trans_left_out);
                    startActivity(intent);
//                    Toast.makeText(DashBord.this, menu[position], Toast.LENGTH_SHORT).show();

                } else if (dashBeen1.get(position - 1).equalsIgnoreCase("Birthday")) {
                    Intent birth_intent = new Intent(DashBord.this, Birthday.class);
                    overridePendingTransition(R.anim.trans_left_in,
                            R.anim.trans_left_out);
                    startActivity(birth_intent);

                } else if (dashBeen1.get(position - 1).equalsIgnoreCase("Holiday")) {

                    Intent birth_intent = new Intent(DashBord.this, Holiday.class);
                    overridePendingTransition(R.anim.trans_left_in,
                            R.anim.trans_left_out);
                    startActivity(birth_intent);


                } else if (dashBeen1.get(position - 1).equalsIgnoreCase("Guidelines")) {
                    Intent birth_intent = new Intent(DashBord.this, Guidelines.class);
                    overridePendingTransition(R.anim.trans_left_in,
                            R.anim.trans_left_out);
                    startActivity(birth_intent);

                } else if (dashBeen1.get(position - 1).equalsIgnoreCase("Announcement")) {
                    Intent birth_intent = new Intent(DashBord.this, Announcement.class);
                    overridePendingTransition(R.anim.trans_left_in,
                            R.anim.trans_left_out);
                    startActivity(birth_intent);

                } else if (dashBeen1.get(position - 1).equalsIgnoreCase("News")) {
                    Intent birth_intent = new Intent(DashBord.this, News.class);
                    overridePendingTransition(R.anim.trans_left_in,
                            R.anim.trans_left_out);
                    startActivity(birth_intent);

                } else if (dashBeen1.get(position - 1).equalsIgnoreCase("NewsLetter")) {
                    Intent birth_intent = new Intent(DashBord.this, Newsletter.class);
                    overridePendingTransition(R.anim.trans_left_in,
                            R.anim.trans_left_out);
                    startActivity(birth_intent);

                } else if (dashBeen1.get(position - 1).equalsIgnoreCase("PaySlip")) {

                    Intent birth_intent = new Intent(DashBord.this, PaySlip.class);
                    overridePendingTransition(R.anim.trans_left_in,
                            R.anim.trans_left_out);
                    startActivity(birth_intent);
                    // Toast.makeText(DashBord.this, "Work in Progress", Toast.LENGTH_SHORT).show();

                }
                else if (dashBeen1.get(position - 1).equalsIgnoreCase("Leave Application")) {

                    Intent leave_intent = new Intent(DashBord.this, LeaveApplication.class);
                    overridePendingTransition(R.anim.trans_left_in,
                            R.anim.trans_left_out);
                    startActivity(leave_intent);
                }
                else if (dashBeen1.get(position - 1).equalsIgnoreCase("Daily Attendance")) {
                    Intent cal_intent = new Intent(DashBord.this, CalenderView.class);
                    overridePendingTransition(R.anim.trans_left_in,
                            R.anim.trans_left_out);
                    startActivity(cal_intent);

                } else if (dashBeen1.get(position - 1).equalsIgnoreCase("Logout")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashBord.this);
                    builder.setMessage("Are you sure you want to logout?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(DashBord.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                            String logout = AppPreferences.savePreferences(DashBord.this, "loginStatus", "0");
                            Log.e("logout", "" + logout);
//                            clearApplicationData();
                            SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.clear();
                            editor.commit();

                            Intent i = new Intent(DashBord.this, Login.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            overridePendingTransition(R.anim.trans_right_in,
                                    R.anim.trans_right_out);
                            dialog.dismiss();


                            finish();


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

                } else {
                   // Toast.makeText(DashBord.this, dashBeen1.get(position - 1), Toast.LENGTH_SHORT).show();


                }


            }

        });




    }


    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_PIC:
                    Bundle bundle = data.getExtras();
                    picBitmap=(Bitmap) bundle.get("data");
                    String str = "";
                    if (picBitmap != null)
                    {

                        str = getStringFromBitmap(picBitmap);
                        AppPreferences.savePreferences(DashBord.this, "Photo", str);

                    }
                    Intent intent=new Intent(DashBord.this,PunchAttendance.class);
                    startActivity(intent);

                    break;

            }
        }
    }


    public static String getStringFromBitmap(Bitmap bm)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }
    @Override
    public void onResume() {
        super.onResume();

//        // Resuming location updates depending on button state and
//        // allowed permissions
//        if (mRequestingLocationUpdates && checkPermissions()) {
//            startLocationUpdates();
//        }
//
//        updateLocationUI();
    }






    private void DashBordApi() {
        pd.setMessage("Loading Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.show();

        RequestQueue sr1 = Volley.newRequestQueue(DashBord.this);
     //   String url1 = "http://103.205.65.52/MobileApp/GetDashbordAttriCount";
        String url1 = Endpoints.DASHBOARDATTRIBUTECOUNT;
        Map<String, Object> jsonParams = new ArrayMap<>();

        if (Cid.equalsIgnoreCase("null")) {
            Cid = "0";
            jsonParams.put("CompanyId", Cid);
        } else {
            Cid = AppPreferences.loadPreferences(DashBord.this, "CompanyId");
            jsonParams.put("CompanyId", Cid);
            jsonParams.put("CompanyCode", AppPreferences.loadPreferences(DashBord.this, "CompanyCode"));

        }

        //jsonParams.put("CompanyId", AppPreferences.loadPreferences(DashBord.this, "CompanyId"));


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url1, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        System.out.println("====DashBordCount===" + response);

                        try {
                            String result = response.getString("result");
                            String Messages = response.getString("Messages");
                            if (result.equalsIgnoreCase("true")) {
                                if (pd != null && pd.isShowing()) {
                                    pd.dismiss();
                                }
                                JSONObject data = response.getJSONObject("data");
                                BirthDayCount = data.getString("BirthDayCount");
                                HolidaysCount = data.getString("HolidaysCount");
                                AnnouncementsCount = data.getString("AnnouncementsCount");
                                NewsCount = data.getString("NewsCount");
                                NewsLettersCount = data.getString("NewsLettersCount");
                                GuidelinesCount = data.getString("GuidelinesCount");

                                AppPreferences.savePreferences(DashBord.this, "BirthDayCount", BirthDayCount);
                                AppPreferences.savePreferences(DashBord.this, "HolidaysCount", HolidaysCount);
                                AppPreferences.savePreferences(DashBord.this, "AnnouncementsCount", AnnouncementsCount);
                                AppPreferences.savePreferences(DashBord.this, "NewsCount", NewsCount);
                                AppPreferences.savePreferences(DashBord.this, "NewsLettersCount", NewsLettersCount);
                                AppPreferences.savePreferences(DashBord.this, "GuidelinesCount", GuidelinesCount);

                                BirthDay = BirthDayCount;
                                Holidays = HolidaysCount;
                                Announcements = AnnouncementsCount;
                                News = NewsCount;
                                NewsLetters = NewsLettersCount;
                                Guidelines = GuidelinesCount;

                                //    Log.e("DATA", ">>>" + BirthDayCount + HolidaysCount + GuidelinesCount + AnnouncementsCount + NewsCount + NewsLettersCount);

                                //   Toast.makeText(DashBord.this, Messages, Toast.LENGTH_SHORT).show();
                            } else {

                                if (pd != null && pd.isShowing()) {
                                    pd.dismiss();
                                }                          //      Toast.makeText(DashBord.this, Messages, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }                        if (null != error.networkResponse) {
                            // Toast.makeText(DashBord.this, "Error", Toast.LENGTH_SHORT).show();
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


    private void getProfile()
    {
        pd.setMessage("Loading Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.show();
        RequestQueue sr2 = Volley.newRequestQueue(DashBord.this);

        Map<String, Object> jsonParams = new ArrayMap<>();
//        jsonParams.put("CompanyId", "ns");
//        jsonParams.put("EmployeeId", "1");
        if (Cid.equalsIgnoreCase("null")) {
            Cid = "0";
            jsonParams.put("CompanyId", Cid);
        } else {
            Cid = AppPreferences.loadPreferences(DashBord.this, "CompanyId");
            jsonParams.put("CompanyId", Cid);
        }
//        jsonParams.put("CompanyId", AppPreferences.loadPreferences(DashBord.this, "CompanyId"));
        jsonParams.put("EmployeeId", AppPreferences.loadPreferences(DashBord.this, "EmployeeId"));
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(DashBord.this, "CompanyCode"));

        Log.e("jsonParams", jsonParams + "");

        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.POST, profile, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("profile", ">>" + response);
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        try {
                            sc_profile.setVisibility(View.VISIBLE);
                            String result = response.getString("result");
                            String Messages = response.getString("Messages");


                            if (result.equalsIgnoreCase("true"))
                            {

                                JSONObject data = response.getJSONObject("data");

                                EmpId = data.getString("EmpId");
                                EmpCode = data.getString("EmpCode");
                                Name = data.getString("Name");
                                Gender = data.getString("Gender");
                                DOB = data.getString("DOB");
                                Log.e(">>>", "" + DOB);
                                DOJ = data.getString("DOJ");
                                Log.e(">>>", "" + DOJ);
                                DOC = data.getString("DOC");
                                FatherName = data.getString("FatherName");
                                SpouseName = data.getString("SpouseName");
                                Photo = data.getString("Photo");
                                PFApply = data.getString("PFApply");
                                PFAppleDate = data.getString("PFAppleDate");
                                PFNo = data.getString("PFNo");
                                PFUAN = data.getString("PFUAN");
                                ESIC_Application = data.getString("ESIC_Application");
                                ESIC_No = data.getString("ESIC_No");
                                PAN = data.getString("PAN");
                                Aadhar = data.getString("Aadhar");
                                PTApplication = data.getString("PTApplication");
                                BranchName = data.getString("BranchName");
                                Grade = data.getString("Grade");
                                Department = data.getString("Department");
                                Designation = data.getString("Designation");
                                Division = data.getString("Division");
                                CostCenter = data.getString("CostCenter");
                                Project = data.getString("Project");
                                Category = data.getString("Category");
                                PaymentMode = data.getString("PaymentMode");
                                BankName = data.getString("BankName");
                                AccountNo = data.getString("AccountNo");
                                IfscCode = data.getString("IfscCode");
                                AccountName = data.getString("AccountName");
                                DOL = data.getString("DOL");
                                String Approver = data.getString("Approver");
                                String Approverid = data.getString("Approver_id");

                                AppPreferences.savePreferences(DashBord.this, "Approver", Approver);
                                AppPreferences.savePreferences(DashBord.this, "Approverid", Approverid);


                                AppPreferences.savePreferences(DashBord.this, "EmpCode", EmpCode);
                                AppPreferences.savePreferences(DashBord.this, "Name", Name);


                                //PersonalDetail = data.getString("PersonalDetail");

                                JSONObject PersonalDetail = data.getJSONObject("PersonalDetail");
                                Address1 = PersonalDetail.getString("Address1");
                                Address2 = PersonalDetail.getString("Address2");
                                City = PersonalDetail.getString("City");
                                State = PersonalDetail.getString("State");
                                Country = PersonalDetail.getString("Country");
                                PinCode = PersonalDetail.getString("PinCode");
                                Address1Per = PersonalDetail.getString("Address1Per");
                                Address2Per = PersonalDetail.getString("Address2Per");
                                CityPer = PersonalDetail.getString("CityPer");
                                StatePer = PersonalDetail.getString("StatePer");
                                CountryPer = PersonalDetail.getString("CountryPer");
                                PinCodePer = PersonalDetail.getString("PinCodePer");
                                Telephone = PersonalDetail.getString("Telephone");
                                MartialStatus = PersonalDetail.getString("MartialStatus");
                                PersonalMobileNo = PersonalDetail.getString("PersonalMobileNo");
                                MarriageDate = PersonalDetail.getString("MarriageDate");
                                OfficialMobileNo = PersonalDetail.getString("OfficialMobileNo");
                                NoOfChildren = PersonalDetail.getString("NoOfChildren");
                                Religion = PersonalDetail.getString("Religion");
                                EmailPersonal = PersonalDetail.getString("EmailPersonal");
                                BloodGroup = PersonalDetail.getString("BloodGroup");
                                EmailOfficial = PersonalDetail.getString("EmailOfficial");
                                Height = PersonalDetail.getString("EmailOfficial");
                                ESICDispensary = PersonalDetail.getString("ESICDispensary");
                                EmergencyContactName = PersonalDetail.getString("EmergencyContactName");
                                Spectacles = PersonalDetail.getString("Spectacles");
                                EmergencyContactNo = PersonalDetail.getString("EmergencyContactNo");


                                JSONObject Passport = data.getJSONObject("Passport");
                                PassportNo = Passport.getString("PassportNo");
                                PassportIssueDate = Passport.getString("PassportIssueDate");
                                PassportExpiryDate = Passport.getString("PassportExpiryDate");
                                PassportPlaceOfIssue = Passport.getString("PassportPlaceOfIssue");
                                VehicleDetails = Passport.getString("VehicleDetails");
                                DrivingLicenseNo = Passport.getString("DrivingLicenseNo");
                                DrivingLicenseIssueDate = Passport.getString("DrivingLicenseIssueDate");
                                DrivingLicenseExpiryDate = Passport.getString("DrivingLicenseExpiryDate");
                                TwoWheeler = Passport.getString("TwoWheeler");
                                FourWheeler = Passport.getString("FourWheeler");
                                DrugLicenseNo = Passport.getString("DrugLicenseNo");
                                DrugIssueDate = Passport.getString("DrugIssueDate");
                                DrugExpiryDate = Passport.getString("DrugExpiryDate");


                                tv_name.setText(Name);


                                String DATE_FORMAT_I = "yyyy-MM-dd'T'HH:mm:ss";
                                String DATE_FORMAT_O = "yyyy-MM-dd";


                                SimpleDateFormat formatInput = new SimpleDateFormat(DATE_FORMAT_I);
                                SimpleDateFormat formatOutput = new SimpleDateFormat(DATE_FORMAT_O);

                                Date date = null;
                                try {
                                    date = formatInput.parse(DOB);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                String dateString = formatOutput.format(date);


                                String[] separated = dateString.split("-");
                                int year = Integer.parseInt(separated[0]);
                                int month = Integer.parseInt(separated[1]);
                                int day = Integer.parseInt(separated[2]);

                                tv_age.setText(getAge(year, month, day));


                                tv_branch.setText(BranchName);
                                tv_dept.setText(Department);
                                tv_designation.setText(Designation);

                                if (!Photo.equalsIgnoreCase("")) {

                                    aq.ajax(Photo, File.class, new AjaxCallback<File>() {
                                        @Override
                                        public void callback(String url, File bm, AjaxStatus status) {

                                            if (bm != null) {
                                                BitmapAjaxCallback cb = new BitmapAjaxCallback();
                                                cb.targetWidth(500).rotate(true);
                                                aq.id(imageView2).image(bm, false, 500, cb);
                                            } else {
                                                imageView2.setImageResource(R.mipmap.register_profile_default);
                                            }
                                        }
                                    });


                                }

                                DashBordApi();
                            } else if (result.equalsIgnoreCase("false")) {
                                if (pd != null && pd.isShowing()) {
                                    pd.dismiss();
                                }                               // Toast.makeText(DashBord.this, Messages, Toast.LENGTH_SHORT).show();

                            } else {
                                if (pd != null && pd.isShowing()) {
                                    pd.dismiss();
                                }                               // Toast.makeText(DashBord.this, Messages, Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            if (pd != null && pd.isShowing()) {
                                pd.dismiss();
                            }
                            e.printStackTrace();

                        }


                        Log.e("ProfileData", "" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (null != error.networkResponse) {
                            if (pd != null && pd.isShowing()) {
                                pd.dismiss();
                            }
                            System.out.println("====response===" + error);
                        }
                    }
                });
        request1.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        sr2.add(request1);
    }

    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        Log.e("age", ">>" + age);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


    private class DrawerAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<String> dashBeen1;
        private LayoutInflater inflater;


        public DrawerAdapter(DashBord dashBord, ArrayList<String> dashBeen1) {
            this.mContext = dashBord;
            inflater = LayoutInflater.from(mContext);
            this.dashBeen1 = dashBeen1;
        }

        @Override
        public int getCount() {
            return dashBeen1.size();
        }

        @Override
        public Object getItem(int position) {
            return dashBeen1.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class Holder {

            TextView tv_name, tv_count;
            RelativeLayout ll_count;
            ImageView dash_image;

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();

            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.dash_list, parent, false);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                holder.tv_count = (TextView) view.findViewById(R.id.tv_count);
                holder.ll_count = (RelativeLayout) view.findViewById(R.id.ll_count);
                holder.dash_image = (ImageView) view.findViewById(R.id.dash_image);
                view.setTag(holder);

            } else {

                holder = (Holder) view.getTag();


            }


            holder.tv_name.setText(dashBeen1.get(position));

            if (dashBeen1.get(position).equals("My Profile")) {

                holder.ll_count.setVisibility(View.GONE);
                holder.tv_count.setVisibility(View.GONE);
                holder.dash_image.setImageResource(R.mipmap.profile);

            } else if (dashBeen1.get(position).equals("Birthday")) {

                holder.tv_count.setVisibility(View.VISIBLE);
                holder.ll_count.setVisibility(View.VISIBLE);
                holder.tv_count.setText(AppPreferences.loadPreferences(DashBord.this, "BirthDayCount"));
                holder.dash_image.setImageResource(R.mipmap.birthday);


            } else if (dashBeen1.get(position).equals("Holiday")) {
                holder.tv_count.setText(AppPreferences.loadPreferences(DashBord.this, "HolidaysCount"));
                holder.tv_count.setVisibility(View.VISIBLE);
                holder.ll_count.setVisibility(View.VISIBLE);
                holder.dash_image.setImageResource(R.mipmap.holiday1);


            } else if (dashBeen1.get(position).equals("Guidelines")) {

                holder.tv_count.setVisibility(View.VISIBLE);
                holder.ll_count.setVisibility(View.VISIBLE);
                holder.tv_count.setText(AppPreferences.loadPreferences(DashBord.this, "GuidelinesCount"));
                holder.dash_image.setImageResource(R.mipmap.guidelines);

            } else if (dashBeen1.get(position).equals("Announcement")) {

                holder.tv_count.setVisibility(View.VISIBLE);
                holder.ll_count.setVisibility(View.VISIBLE);
                holder.tv_count.setText(AppPreferences.loadPreferences(DashBord.this, "AnnouncementsCount"));
                holder.dash_image.setImageResource(R.mipmap.announcement);

            } else if (dashBeen1.get(position).equals("News")) {

                holder.tv_count.setVisibility(View.VISIBLE);
                holder.ll_count.setVisibility(View.VISIBLE);
                holder.tv_count.setText(AppPreferences.loadPreferences(DashBord.this, "NewsCount"));
                holder.dash_image.setImageResource(R.mipmap.news);

            } else if (dashBeen1.get(position).equals("NewsLetter")) {

                holder.tv_count.setVisibility(View.VISIBLE);
                holder.ll_count.setVisibility(View.VISIBLE);
                holder.tv_count.setText(AppPreferences.loadPreferences(DashBord.this, "NewsLettersCount"));
                holder.dash_image.setImageResource(R.mipmap.newsletter);

            } else if (dashBeen1.get(position).equals("PaySlip")) {

                holder.tv_count.setVisibility(View.INVISIBLE);
                holder.ll_count.setVisibility(View.INVISIBLE);
                holder.dash_image.setImageResource(R.mipmap.payslip);

            } else if (dashBeen1.get(position).equals("Leave Application")) {
                holder.tv_count.setVisibility(View.INVISIBLE);
                holder.ll_count.setVisibility(View.INVISIBLE);
                holder.dash_image.setImageResource(R.mipmap.leaveapplication);

            } else if (dashBeen1.get(position).equals("Daily Attendance")) {
                holder.ll_count.setVisibility(View.INVISIBLE);
                holder.tv_count.setVisibility(View.INVISIBLE);
                holder.dash_image.setImageResource(R.mipmap.attendance);

            } else if (dashBeen1.get(position).equals("Logout")) {

                holder.tv_count.setVisibility(View.INVISIBLE);
                holder.ll_count.setVisibility(View.INVISIBLE);
                holder.dash_image.setImageResource(R.mipmap.logout);

            }


            return view;
        }
    }



}