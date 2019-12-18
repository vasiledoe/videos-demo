package com.racovita.videosdemo.utils.extensions

import io.reactivex.disposables.Disposable

fun Disposable?.safelyDispose() = apply {
    if (this != null && !this.isDisposed) {
        this.dispose()
    }
}