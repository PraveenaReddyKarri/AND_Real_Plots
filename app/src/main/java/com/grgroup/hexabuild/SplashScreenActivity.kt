package com.grgroup.hexabuild

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.grgroup.hexabuild.utils.SharedPref


class SplashScreenActivity : AppCompatActivity() {
    var handler: Handler? = null
    var runnable: Runnable? = null
    private var pref: SharedPref? = null
    lateinit var navHostFragment: NavHostFragment

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler(mainLooper)
        runnable = Runnable {


            val mainIntent = Intent(this, MainActivity::class.java)
            this.startActivity(mainIntent)
            this.finish()


//            navHostFragment =
//                (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
//            val inflater = navHostFragment.navController.navInflater
//            val graph = inflater.inflate(R.navigation.nav_graph)
//
//              pref= this.let { SharedPref(it).getmSharedPrefInstance() }
//
//            if (SharedPref(this).getUserId().equals("")) {
//                graph.startDestination = R.id.loginFragment
//
//            } else {
//                graph.startDestination = R.id.dashboardFragment
//
//
//            }
//
//            navHostFragment.navController.graph = graph

        }
        handler!!.postDelayed(runnable!!, 2000)
    }
}