package com.racovita.videosdemo.utils.extensions

import com.racovita.videosdemo.R
import com.racovita.videosdemo.utils.helper.ResUtil

fun Throwable.getPrettyErrorMessage(resUtil: ResUtil): String {
    val cause = this.cause.toString()

    return if (cause.contains("Unable to resolve host")) {
        resUtil.getStringRes(R.string.err_bad_connection)

    } else {
        resUtil.getStringRes(R.string.err_ops)
    }
}