package com.racovita.videosdemo.utils.helper

import android.content.Context

class ResUtil(private val context: Context) {

    fun getStringRes(strId: Int): String = context.resources.getString(strId)
}