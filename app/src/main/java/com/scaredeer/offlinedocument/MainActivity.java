package com.scaredeer.offlinedocument;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private static final String OFFLINE_DOCUMENT_ROOT = "file:///android_asset/offline_document";
    private static final String HOME_DOCUMENT = "index.html";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWebView = new WebView(this);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(OFFLINE_DOCUMENT_ROOT + "/" + HOME_DOCUMENT);

        setContentView(mWebView);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}