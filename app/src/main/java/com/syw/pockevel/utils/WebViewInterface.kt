package com.syw.pockevel.utils

import android.util.Log
import android.webkit.JavascriptInterface
import com.adzerk.android.sdk.rest.Decision
import com.syw.pockevel.MainActivity
import com.syw.pockevel.utils.KevelManager.registerFirePixel

class WebViewInterface(private val decision: Decision?, private val callback: (String) -> Unit) {

    @JavascriptInterface
    fun postMessage(url: String) {
        decision?.let {
            registerFirePixel(
                it.clickUrl,
                {
                    Log.i(MainActivity.TAG, "Click registered in kevel")
                    callback(url)
                },
                {
                    Log.i(MainActivity.TAG, "Error while try to register event")
                }
            )
        }
    }
}