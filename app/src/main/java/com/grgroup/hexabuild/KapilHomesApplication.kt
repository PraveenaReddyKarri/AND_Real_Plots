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


//    @Override
//    public void onCreate()
//    {
//        super.onCreate()
////        Fabric.with(this, new Crashlytics());
//        //        Fabric.with(this, new Crashlytics());
//        KapilHomesApplication.appInstance = this
//    }



    fun getInstance(): KapilHomesApplication? {

        if (appInstance ==null){
            appInstance =
                KapilHomesApplication()

        }
        return appInstance

    }
}
}