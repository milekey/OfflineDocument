package com.scaredeer.offlinedocument

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

private const val OFFLINE_DOCUMENT_ROOT = "file:///android_asset/offline_document"
private const val HOME_DOCUMENT = "index.html"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("$OFFLINE_DOCUMENT_ROOT/$HOME_DOCUMENT")

        setContentView(webView)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    finish()
                }
            }
        })
    }
}