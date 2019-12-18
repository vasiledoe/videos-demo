package com.racovita.videosdemo.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkConnectivity(private val context: Context) {

    /**
     * Check connection
     */
    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            connectivityManager.activeNetworkInfo?.let { networkInfo ->
                return networkInfo.isConnected
            }

        } else {
            connectivityManager.activeNetwork?.let { network ->
                connectivityManager.getNetworkCapabilities(network)?.let { netCap ->
                    return netCap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            netCap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                }
            }
        }

        return false
    }
}