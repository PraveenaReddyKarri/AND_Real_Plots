package com.example.kapilhomes.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class InternetConnection {
    //    public boolean  isNetworkConnected(Activity activity) {
    //        ConnectivityManager cm = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
    //
    //        return cm.getActiveNetworkInfo() != null;
    //    }
    fun isNetworkConnected(activity: Activity): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT < 23) {
                val ni = connectivityManager.activeNetworkInfo
                if (ni != null) {
                    return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)
                }
            } else {
                val n = connectivityManager.activeNetwork
                if (n != null) {
                    val nc = connectivityManager.getNetworkCapabilities(n)
                    if (nc != null) {
                        return nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                            NetworkCapabilities.TRANSPORT_WIFI
                        )
                    }
                }
            }
        }
        return false
    }
}