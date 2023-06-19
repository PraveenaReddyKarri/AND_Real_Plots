package com.grgroup.hexabuild

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class KapilHomesApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        appInstance = this
        Fresco.initialize(getApplicationContext());

    }

    companion object {
    private var appInstance: KapilHomesApplication? = null


        fun getInstance(): KapilHomesApplication? {

        if (appInstance ==null){
            appInstance =
                KapilHomesApplication()

        }
        return appInstance

    }
}
}