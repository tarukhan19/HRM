package com.hrm.hrm;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.google.android.gms.common.ConnectionResult;
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
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.hrm.hrm.FCM.Config;
import com.hrm.hrm.utils.AppConstants;
import com.hrm.hrm.utils.AppPreferences;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.hrm.hrm.utils.Endpoints;

import java.util.ArrayList;
import java.util.List;

public class PunchAttendance extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    String date="", time1="", time2="", pic="";
    Geocoder geocoder;
    double latitude = 0.0;
    double longitude = 0.0;
    Button punchButton;
    TextView timeTV, dateTV, addressTV,tv;
    Intent intent;
    String address = "", resultgps;
    ProgressDialog pd1;
    LocationManager manager;
    LinearLayout reload;
    String from = "", activityname="";
    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private Location mylocation = null;
    private byte[] profilePic = null;
    String regId = "";
    ArrayAdapter adapter;
    ListView listView;
    ArrayList<String> punchArray;
    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_attendance);
        intent = getIntent();

        activityname = intent.getStringExtra("from");


        listView = (ListView) findViewById(R.id.mobile_list);
        tv=findViewById(R.id.tv);
        punchArray=new ArrayList<>();
        adapter = new ArrayAdapter<String>(PunchAttendance.this,
                R.layout.activity_listview, punchArray);

        String Cid = AppPreferences.loadPreferences(PunchAttendance.this, "CompanyId");
        String CompanyCode = AppPreferences.loadPreferences(PunchAttendance.this, "CompanyCode");

        Endpoints endpoints = new Endpoints(CompanyCode);


        pic = AppPreferences.loadPreferences(PunchAttendance.this, "Photo");
        if (!pic.isEmpty()) {
            profilePic = Base64.decode(pic, Base64.NO_WRAP);
        }


        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        regId = telephonyManager.getDeviceId();


        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        punchButton = findViewById(R.id.punchButton);
        timeTV = findViewById(R.id.time);
        dateTV = findViewById(R.id.date);
        addressTV = findViewById(R.id.address);
        intent = getIntent();
        reload = findViewById(R.id.reload);
        setTimerTask();
        setUpGClient();
        getMyLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        dateTV.setText(formattedDate);


        if (activityname.equalsIgnoreCase("punch"))
        {
            punchButton.setText("Punch Attendance");
            tv.setVisibility(View.VISIBLE);
            loadAttendance();
        }
        else
        {
            punchButton.setText("Register Location");
            tv.setVisibility(View.GONE);

        }

        punchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (activityname.equalsIgnoreCase("punch"))
                {
                    from = "punch";
                    getMyLocation();
                }
                else
                {
                    from = "locationreg";
                    getMyLocation();

                }



            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                from = "reload";
                getMyLocation();

            }
        });


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(12);
        mMap.setIndoorEnabled(true);
        mMap.clear();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        LatLng latLng = new LatLng(latitude, longitude);

        //    Log.e("maplong", latitude + " " + longitude);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        mMap.addMarker(markerOptions.title(address));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }


    private void setTimerTask() {
        long delay = 3000;
        long periodToRepeat = 60 * 1000; /* 1 mint */
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
                        String currentDateandTime = formate.format(new Date());
                        String[] separated = currentDateandTime.split(" ");
                        date = separated[0];
                        time1 = separated[1];
                        time2 = separated[2];
                        timeTV.setText(time1 + " " + time2);


                    }
                });
            }
        }, 1000, 1000);
    }


    private void PunchAttendence() {
        pd1 = new ProgressDialog(PunchAttendance.this);
        pd1.setMessage("Loading Please Wait...");
        pd1.setCanceledOnTouchOutside(false);
        pd1.show();

        RequestQueue sr1 = Volley.newRequestQueue(PunchAttendance.this);
        String url1 = Endpoints.PUNCHATTENDANCE;

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("EmployeeId", AppPreferences.loadPreferences(PunchAttendance.this, "EmployeeId"));
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(PunchAttendance.this, "CompanyCode"));
        jsonParams.put("CompanyId", AppPreferences.loadPreferences(PunchAttendance.this, "CompanyId"));
        jsonParams.put("Photo", profilePic);
        jsonParams.put("location", address);
        jsonParams.put("latitude", latitude);
        jsonParams.put("longitude", longitude);
        jsonParams.put("device_id",regId );
        Log.e("params", jsonParams.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url1, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        System.out.println("====Attendence===" + response);

                         Log.e("punch", response + "");

                        try {
                            String result = response.getString("result");
                            String Messages = response.getString("Messages");
                            if (result.equals("true")) {
                                if (pd1 != null && pd1.isShowing()) {
                                    pd1.dismiss();
                                }
                                address = "";
//{"result":false,"Messages":"Punch failed beacouse you are not in defined location","data":false}
                                // Toast.makeText(DashBord.this, "Punching successfully done", Toast.LENGTH_SHORT).show();
                                SuccessDialog("Punching successfully done");
                                JSONObject data = response.getJSONObject("data");
                                loadAttendance();

                                System.out.println("====Attendence===" + Messages);

                            } else if (result.equalsIgnoreCase("false")
                                    && Messages.equalsIgnoreCase("Punch failed beacouse you are not in defined location")){

                                if (pd1 != null && pd1.isShowing()) {
                                    pd1.dismiss();
                                }
                                SuccessDialog("Punch failed because you are not in define location");
                                //  Toast.makeText(PunchAttendance.this, Messages, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (pd1 != null && pd1.isShowing()) {
                            pd1.dismiss();
                        }
                        if (null != error.networkResponse) {
                            // Toast.makeText(PunchAttendance.this, "Error", Toast.LENGTH_SHORT).show();
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

//    private String displayFirebaseRegId() {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        regId = pref.getString("regId", null);
//        try {
//            //   Log.e("regId", "" + regId);
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//        return regId;
//    }

    private void SuccessDialog(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;
        if (mylocation != null) {
            latitude = mylocation.getLatitude();
            longitude = mylocation.getLongitude();

            List<Address> addresses;
            geocoder = new Geocoder(PunchAttendance.this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                addressTV.setText(address);
            } catch (IOException e) {
                e.printStackTrace();
            }

            LatLng latLng = new LatLng(latitude, longitude);
            MarkerOptions markerOptions = new MarkerOptions();
            mMap.clear();

            markerOptions.position(latLng);
            mMap.addMarker(markerOptions.title(address));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        checkPermissions();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Do whatever you need
        //You can display a message here
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //You can display a message here
    }


    private void getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(PunchAttendance.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    //  locationRequest.setInterval(1);
                    // locationRequest.setFastestInterval(1);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(PunchAttendance.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);

                                        //       Log.e("latlng", latitude + "" + longitude);


                                        if (from.equalsIgnoreCase("punch")  || from.equalsIgnoreCase("locationreg"))
                                        {

                                           try {
                                               if (latitude != 0.0 && longitude != 0.0 &&
                                                       !address.isEmpty() && !regId.isEmpty())
                                               {

                                                   if (activityname.equalsIgnoreCase("punch"))
                                                   {
                                                       PunchAttendence();
                                                   }
                                                   else if (activityname.equalsIgnoreCase("locationdes"))
                                                   {
                                                       registerLocation();

                                                   }


                                               }
                                           }
                                           catch (Exception e)
                                           {}

                                        }
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        //  Log.e("latlng", "REQUEST_CHECK_SETTINGS_GPS");

                                        status.startResolutionForResult(PunchAttendance.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    //   Log.e("latlng", "SETTINGS_CHANGE_UNAVAILABLE");

                                    break;

                                case ConnectionResult.SERVICE_MISSING:
                                    // Toast.makeText(PunchAttendance.this, "Google Play Services Missing", Toast.LENGTH_SHORT).show();
                                    break;
                                case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                                    //The user has to update play services
                                    //  Toast.makeText(PunchAttendance.this, "Update Google Play Services", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }

    private void registerLocation()
    {
        pd1 = new ProgressDialog(PunchAttendance.this);
        pd1.setMessage("Loading Please Wait...");
        pd1.setCanceledOnTouchOutside(false);
        pd1.show();

        RequestQueue sr1 = Volley.newRequestQueue(PunchAttendance.this);
        String url1 = Endpoints.LOCATIONREGISTER;

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("EmployeeId", AppPreferences.loadPreferences(PunchAttendance.this, "EmployeeId"));
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(PunchAttendance.this, "CompanyCode"));
        jsonParams.put("CompanyId", AppPreferences.loadPreferences(PunchAttendance.this, "CompanyId"));
        jsonParams.put("allowLatitude", latitude);
        jsonParams.put("allowLongitude", longitude);
        jsonParams.put("allow_location", address);

        Log.e("params", jsonParams.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url1, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



                         Log.e("punch", response + "");

                        try {
                            String result = response.getString("result");
                            String Messages = response.getString("Messages");
                            if (result.equals("true")) {
                                if (pd1 != null && pd1.isShowing()) {
                                    pd1.dismiss();
                                }
                                address = "";

                                // Toast.makeText(DashBord.this, "Punching successfully done", Toast.LENGTH_SHORT).show();
                                SuccessDialog("Location registration successfully done");
                                JSONObject data = response.getJSONObject("data");


                                System.out.println("====Attendence===" + Messages);

                            } else {


                                //  Toast.makeText(PunchAttendance.this, Messages, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (pd1 != null && pd1.isShowing()) {
                            pd1.dismiss();
                        }
                        if (null != error.networkResponse) {
                            // Toast.makeText(PunchAttendance.this, "Error", Toast.LENGTH_SHORT).show();
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


    private void loadAttendance()
    {
        pd1 = new ProgressDialog(PunchAttendance.this);
        pd1.setMessage("Loading Please Wait...");
        pd1.setCanceledOnTouchOutside(false);
        pd1.show();

        RequestQueue sr1 = Volley.newRequestQueue(PunchAttendance.this);
        String url1 = Endpoints.LOADPUNCH;

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("EmployeeId", AppPreferences.loadPreferences(PunchAttendance.this, "EmployeeId"));
        jsonParams.put("CompanyCode", AppPreferences.loadPreferences(PunchAttendance.this, "CompanyCode"));

        Log.e("params", jsonParams.toString());

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url1, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



                        Log.e("punch", response + "");

                        try {
                            String result = response.getString("result");
                            String Messages = response.getString("Messages");
                            if (result.equals("true")) {
                                punchArray.clear();
                                if (pd1 != null && pd1.isShowing()) {
                                    pd1.dismiss();
                                }

                                JSONArray dataarray=response.getJSONArray("data");
                                for (int i=0; i<dataarray.length();i++)
                                {
                                    JSONObject jsonObject=dataarray.getJSONObject(i);
                                    String punch_time=jsonObject.getString("punch_time");
                                    punchArray.add(punch_time);

                                }


                                listView.setAdapter(adapter);



                            } else {


                                //  Toast.makeText(PunchAttendance.this, Messages, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (pd1 != null && pd1.isShowing()) {
                            pd1.dismiss();
                        }
                        if (null != error.networkResponse) {
                            // Toast.makeText(PunchAttendance.this, "Error", Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
        }
    }

    private void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(PunchAttendance.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        } else {
            getMyLocation();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(PunchAttendance.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }


}
