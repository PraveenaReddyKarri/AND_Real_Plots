package com.grgroup.hexabuild.sitevisit

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResult
import com.grgroup.hexabuild.MainActivity
import com.grgroup.hexabuild.servicesapi.Connect
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class SiteLocation(private val context: Context, var callback: LocationCallback) :android.location.LocationListener {
    protected var locationManager: LocationManager? = null
    private val connect: Connect? = null
    fun locationPermissions(activity: Activity) {
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION
                )
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        getLocation()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        if (response.isPermanentlyDenied) {
                            showSettingsDialog(activity)
                        } else {
//                            showSettingsDialog(activity);
                            locationPermissions(activity)
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).check()
    }

    fun removeLocation(){
        locationManager?.removeUpdates(this)


    }

    private fun showSettingsDialog(activity: Activity) {
        // we are displaying an alert dialog for permissions
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog, which ->
            dialog.cancel()
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
            //                activity.startActivityForResult(intent,2);
            (activity as MainActivity).activityResultHandler?.launch(intent) { result: ActivityResult? -> locationPermissions(activity) }
        }
        builder.setNegativeButton("Cancel") {
                dialog: DialogInterface, which: Int -> dialog.cancel()
            callback.onNewLocationAvailable(null)

        }
        builder.show()
    }

     fun getLocation(){

            try {
                locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if(locationManager!=null){
                    var gpsavailable= locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    var networkavailable= locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                    if(!gpsavailable &&!networkavailable){
                        locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)

                    } else if(gpsavailable){
                        locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)

                        locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                    } else if(networkavailable){
                        locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5f, this)
                        locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                    }

                }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }

//    val location: Unit
//        get() {
//            try {
//                locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//                locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
//            } catch (e: SecurityException) {
//                e.printStackTrace()
//            }
//        }
    fun statusCheck(activity: Activity) {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps(activity)
        }
    }

    private fun buildAlertMessageNoGps(activity: Activity) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id -> activity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
                .setNegativeButton("No") { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    @SuppressLint("MissingPermission")
    fun getLocations() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager != null) {
            val gpsavailable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val networkavailable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!gpsavailable && !networkavailable) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
            } else if (gpsavailable) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            } else if (networkavailable) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5f, this)
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
        }
    }


    override fun onLocationChanged(location: Location) {
//        showToast("Current Location: " + location.latitude + ", " + location.longitude + ", " + location.time)
        showToast("Current Location updated")
        callback.onNewLocationAvailable(location)
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {
        showToast("Please Enable GPS and Internet")
    }

    override fun onProviderDisabled(provider: String) {}
    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}