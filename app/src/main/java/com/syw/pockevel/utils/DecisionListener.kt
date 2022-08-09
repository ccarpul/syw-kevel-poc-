package com.syw.pockevel.utils

import com.adzerk.android.sdk.AdzerkSdk
import com.adzerk.android.sdk.rest.DecisionResponse

class DecisionListener(
    val onSuccess: (DecisionResponse?) -> Unit,
    val onError: (AdzerkSdk.AdzerkError?) -> Unit,
) : AdzerkSdk.DecisionListener {

    override fun success(response: DecisionResponse?) {
        onSuccess(response)
    }

    override fun error(error: AdzerkSdk.AdzerkError?) {
        onError(error)
    }
}