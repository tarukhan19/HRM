package com.hrm.hrm;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;

import com.hrm.hrm.utils.AppPreferences;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

public class MainActivity extends Activity {
    String loginStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CookieManager manager = new CookieManager();
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#A5C1EB"));

        }


        loginStatus = AppPreferences.loadPreferences(MainActivity.this, "loginStatus");

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                if (loginStatus.matches("1")) {
                    Intent Loginintent = new Intent(MainActivity.this, DashBord.class);
                    Loginintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(Loginintent);
//                    overridePendingTransition(R.anim.trans_left_in,
//                            R.anim.trans_left_out);
                    finish();

                } else if (loginStatus.matches("0")) {
                    Intent Loginintent = new Intent(MainActivity.this, Login.class);
                    Loginintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(Loginintent);
//                    overridePendingTransition(R.anim.trans_left_in,
//                            R.anim.trans_left_out);
                    finish();

                } else {

                    Intent intent = new Intent(MainActivity.this, Login.class);
                    Intent Loginintent = new Intent(MainActivity.this, Login.class);
                    Loginintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(Loginintent);
//                    overridePendingTransition(R.anim.trans_left_in,
//                            R.anim.trans_left_out);
                    finish();

//
//
//                    Intent intent=new Intent(MainActivity.this,PunchAttendance.class);
//                    startActivity(intent);
                }


            }
        }, 2000);


    }


    @Override
    protected void onResume()  {
        super.onResume();
        forceUpdate();
    }
    public void forceUpdate(){
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo =  packageManager.getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String currentVersion = packageInfo.versionName;
        new ForceUpdateAsync(currentVersion,MainActivity.this).execute();
    }

}
