package com.scaredeer.offlinedocument

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.scaredeer.offlinedocument.ui.theme.AppTheme

private const val OFFLINE_DOCUMENT_ROOT = "file:///android_asset/offline_document"
private const val HOME_DOCUMENT = "index.html"

/**
 * cf. https://developer.android.com/develop/ui/compose/migrate/interoperability-apis/wrap-webview-in-compose?hl=ja
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold { innerPadding ->
                    PersistentWebView(
                        url = "$OFFLINE_DOCUMENT_ROOT/$HOME_DOCUMENT",
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
fun PersistentWebView(url: String, modifier: Modifier = Modifier, onBack: () -> Unit) {
    val webViewStateBundle = rememberSaveable { Bundle() }

    // Hold a reference to the WebView to check its history state
    var webViewReference by remember { mutableStateOf<WebView?>(null) }

    // Intercept the system back press if the WebView has history
    BackHandler(enabled = true) {
        val webView = webViewReference
        if (webView != null && webView.canGoBack()) {
            webView.goBack() // Go back in history
        } else {
            // Remove the WebView from its parent ViewGroup.
            (webView?.parent as? ViewGroup)?.removeView(webView)
            // Stop active loading and clear history.
            webView?.stopLoading()
            webView?.clearHistory()
            // Destroy the instance.
            webView?.destroy()

            onBack() // Exit screen
        }
    }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()

                // Restore the state and history
                if (webViewStateBundle.containsKey("WEBVIEW_STATE")) {
                    restoreState(webViewStateBundle.getBundle("WEBVIEW_STATE")!!)
                } else {
                    loadUrl(url)
                }

                webViewReference = this
            }
        },
        onRelease = { releasedWebView ->
            // Save navigation history before the instance is destroyed
            val bundle = Bundle()
            releasedWebView.saveState(bundle)
            webViewStateBundle.putBundle("WEBVIEW_STATE", bundle)

            webViewReference = null
        },
        modifier = modifier
    )
}