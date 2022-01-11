package com.grgroup.hexabuild.newreferal

import android.location.Location

interface LocationCallback {
    fun onNewLocationAvailable(location: Location?)
}