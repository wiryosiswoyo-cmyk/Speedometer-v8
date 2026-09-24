package com.v8.ecudash;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.*;
import android.view.WindowManager;
import androidx.webkit.WebViewAssetLoader;

public class MainActivity extends Activity {
    WebView web;

    public void onCreate(Bundle b) {
        super.onCreate(b);

        getWindow().addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );

        requestPermissions(
            new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            },
            100
        );

        web = new WebView(this);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setGeolocationEnabled(true);

        WebViewAssetLoader l =
            new WebViewAssetLoader.Builder()
                .addPathHandler(
                    "/assets/",
                    new WebViewAssetLoader.AssetsPathHandler(this)
                )
                .build();

        web.setWebViewClient(new WebViewClient() {
            public WebResourceResponse shouldInterceptRequest(
                    WebView v,
                    WebResourceRequest r
            ) {
                return l.shouldInterceptRequest(r.getUrl());
            }
        });

        web.setWebChromeClient(new WebChromeClient() {
            public void onGeolocationPermissionsShowPrompt(
                    String o,
                    GeolocationPermissions.Callback c
            ) {
                c.invoke(o, true, false);
            }
        });

        setContentView(web);

        web.loadUrl(
            "https://appassets.androidplatform.net/assets/index.html"
        );
    }
}
