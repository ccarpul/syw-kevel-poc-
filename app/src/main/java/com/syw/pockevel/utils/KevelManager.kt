package com.syw.pockevel.utils

import com.adzerk.android.sdk.AdzerkSdk
import com.adzerk.android.sdk.rest.Decision
import com.adzerk.android.sdk.rest.FirePixelResponse
import com.adzerk.android.sdk.rest.Placement
import com.adzerk.android.sdk.rest.Request
import com.syw.pockevel.BuildConfig.NETWORK_ID

object KevelManager {

    fun adzerkSdkInstance(): AdzerkSdk? = AdzerkSdk
        .Builder()
        .networkId(NETWORK_ID)
        .build()


    fun getRequestInstance(placement: Placement): Request =
        Request
            .Builder()
            .addPlacement(placement)
            .build()

    fun Decision.registerFirePixel(
        fireType: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        adzerkSdkInstance()?.let {
            it.firePixel(
                fireType,
                object : AdzerkSdk.FirePixelListener {
                    override fun success(response: FirePixelResponse?) {
                        onSuccess()
                    }

                    override fun error(error: AdzerkSdk.AdzerkError?) {
                        onError()
                    }
                }
            )
        }
    }
}

