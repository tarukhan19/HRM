package com.hrm.hrm;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.hrm.hrm.utils.AppPreferences;
import com.hrm.hrm.utils.Endpoints;

public class WebViewActivity extends Activity {
    LinearLayout ll_back;
    WebView wv_doc;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#A5C1EB"));

        }

        String Cid = AppPreferences.loadPreferences(WebViewActivity.this, "CompanyId");

        String CompanyCode= AppPreferences.loadPreferences(WebViewActivity.this, "CompanyCode");

        Endpoints endpoints=new Endpoints(CompanyCode);



        url = AppPreferences.loadPreferences(WebViewActivity.this , "File");
        wv_doc = (WebView) findViewById(R.id.wv_doc);
        wv_doc.getSettings().setJavaScriptEnabled(true);
//        wv_doc.getSettings().setPluginsEnabled(true);
        wv_doc.getSettings().setAllowFileAccess(true);
        wv_doc.loadUrl(url);


        wv_doc.getSettings().setBuiltInZoomControls(false);
        wv_doc.setVerticalScrollBarEnabled(false);
        wv_doc.clearCache(true);
        wv_doc.setFocusable(true);
        wv_doc.setHorizontalScrollBarEnabled(false);
        wv_doc.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv_doc.getSettings().setJavaScriptEnabled(true);
        wv_doc.getSettings().setAppCacheEnabled(true);
        wv_doc.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv_doc.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv_doc.getSettings().setAllowFileAccess(true);
        wv_doc.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wv_doc.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.trans_right_in,
                        R.anim.trans_right_out);
            }
        });

    }
}
