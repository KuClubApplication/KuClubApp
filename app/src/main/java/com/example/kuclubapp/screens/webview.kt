package com.example.kuclubapp.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.kuclubapp.viewmodel.NavClubViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun openWebView(navClubViewModel: NavClubViewModel) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.builtInZoomControls = true
                settings.defaultTextEncodingName = "utf-8"
            }
        },
        update = { webView ->
            navClubViewModel.selectedClub?.clubInsta?.let { webView.loadUrl(it) }
        }
    )
}