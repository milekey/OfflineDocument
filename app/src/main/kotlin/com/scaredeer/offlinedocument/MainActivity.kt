package com.scaredeer.offlinedocument

import android.content.res.Configuration
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

private const val OFFLINE_DOCUMENT_ROOT = "file:///android_asset/offline_document"
private const val HOME_DOCUMENT = "index.html"

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this)
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

    /*
    cf. Manage WebView state
    https://developer.android.com/develop/ui/compose/quick-guides/content/manage-webview-state
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        webView.invalidate()
    }
}