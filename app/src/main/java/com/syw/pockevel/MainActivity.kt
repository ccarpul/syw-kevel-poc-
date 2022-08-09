package com.syw.pockevel

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.adzerk.android.sdk.AdzerkSdk
import com.adzerk.android.sdk.rest.DecisionResponse
import com.adzerk.android.sdk.rest.Placement
import com.syw.pockevel.BuildConfig.AD_TYPES
import com.syw.pockevel.BuildConfig.SIDE_ID
import com.syw.pockevel.databinding.ActivityMainBinding
import com.syw.pockevel.utils.DecisionListener
import com.syw.pockevel.utils.KevelManager.adzerkSdkInstance
import com.syw.pockevel.utils.KevelManager.getRequestInstance
import com.syw.pockevel.utils.WebViewInterface

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val placement by lazy { Placement("div0", SIDE_ID, AD_TYPES) }

    companion object {
        const val JAVASCRIPT_INTERFACE_NAME = "SywWayWebView"
        private const val MIME_TYPE = "text/html"
        private const val ENCODING = "UTF-8"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDecisions()
    }

    private fun getDecisions() =
        adzerkSdkInstance()
            ?.requestPlacement(
                getRequestInstance(placement),
                DecisionListener(
                    onSuccess = ::processSuccess,
                    onError = ::processError
                )
            )

    private fun processSuccess(response: DecisionResponse?) {
        binding.progressBar.visibility = View.VISIBLE
        Log.i(javaClass.simpleName, response?.decisions?.values.toString())
        renderWebView(response)
    }

    private fun processError(error: AdzerkSdk.AdzerkError?) {
        Log.i(javaClass.simpleName, error?.reason.orEmpty())
        binding.progressBar.visibility = View.GONE
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun renderWebView(response: DecisionResponse?) = with(binding.webview) {
        webViewClient = WebClientAddCard()
        settings.javaScriptEnabled = true
        val decision = response
            ?.decisions
            ?.get(placement.divName)
            ?.get(0)

        decision?.contents?.get(0)?.body?.let { data ->
                loadDataWithBaseURL("", data, MIME_TYPE, ENCODING, "")
            }

        addJavascriptInterface(
            WebViewInterface(decision),
            JAVASCRIPT_INTERFACE_NAME
        )
    }

    inner class WebClientAddCard : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            binding.progressBar.visibility = View.GONE
        }
    }
}