package com.syw.pockevel.utils

import android.util.Log
import android.webkit.JavascriptInterface
import com.adzerk.android.sdk.rest.Decision
import com.syw.pockevel.utils.KevelManager.registerFirePixel

class WebViewInterface(private val decision: Decision?) {

    /** Show a toast from the web page  */
    @JavascriptInterface
    fun postMessageClick() {
        Log.i(javaClass.simpleName, "Register Click")
        decision?.let {
            it.registerFirePixel(
                decision.clickUrl,
                {

                },
                {

                }
            )
        }
    }



    fun postMessageImpression() {
        Log.i(javaClass.simpleName, "Register Impression")
        decision?.let {
            it.registerFirePixel(
                decision.impressionUrl,
                {

                },
                {

                }
            )
        }
    }
}