package com.grgroup.hexabuild.sitevisit

import android.location.Location

interface LocationCallback {
    fun onNewLocationAvailable(location: Location?)
}