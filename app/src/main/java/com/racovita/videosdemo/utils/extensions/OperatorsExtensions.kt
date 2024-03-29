package com.racovita.videosdemo.utils.extensions

import androidx.lifecycle.MutableLiveData

operator fun <T> MutableLiveData<ArrayList<T>>.plusAssign(values: List<T>) {
    val value = this.value ?: arrayListOf()
    value.addAll(values)
    this.value = value
}