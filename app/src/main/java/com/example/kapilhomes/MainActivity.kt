package com.example.kapilhomes

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.kapilhomes.dashboard.DashboardFragment
import com.example.kapilhomes.databinding.ActivityMainBinding
import com.example.kapilhomes.newreferal.NewReferral
import com.example.kapilhomes.utils.SharedPref
import com.example.kapilhomes.venturelist.VentureListFragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

    var binding: ActivityMainBinding? = null
    private var pref: SharedPref? = null
    private var hView: View? = null

    private var menuItem: MenuItem? = null

    lateinit var navHostFragment: NavHostFragment
    val versionName = BuildConfig.VERSION_NAME


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding?.toolbar)
//        supportActionBar!!.setDisplayShowTitleEnabled(false)
//        toggle.isDrawerIndicatorEnabled = true


//        val window: Window = MainActivity.getWindow()
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        hView = binding?.navView?.getHeaderView(0)

        val release = hView!!.findViewById<TextView>(R.id.version)


        release.text = " Version  $versionName"

        initviews()
        setNavigationView();

        HeaderProfile()
        binding?.toolbar?.setTitleTextAppearance(this, R.style.RobotoBoldTextAppearance);

        val oldFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (oldFragment != null && oldFragment is DashboardFragment) {
            supportFragmentManager
                .beginTransaction().remove(oldFragment).commit()
        }

        hView!!.findViewById<View>(R.id.dashboard).setOnClickListener(this)
        hView!!.findViewById<View>(R.id.layouts).setOnClickListener(this)
        hView!!.findViewById<View>(R.id.nav_logout).setOnClickListener(this)
        hView!!.findViewById<View>(R.id.newrefferels).setOnClickListener(this)


        navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        if (SharedPref(this).getUserId().equals("")) {
            graph.startDestination = R.id.loginFragment

        } else {
            graph.startDestination = R.id.dashboardFragment


        }
        navHostFragment.navController.graph = graph


    }

    private fun setNavigationView() {

        val mNavigationView = findViewById<View>(R.id.nav_view) as NavigationView

        mNavigationView.setNavigationItemSelectedListener(this)
    }


    override fun onBackPressed() {

            super.onBackPressed()

    }



    private fun initviews() {
        val actionBarDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, binding!!.drawerLayout,
            binding!!.toolbar, R.string.app_name,
            R.string.app_name
        ) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
        }
        actionBarDrawerToggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
        //        binding.toolbar.setContentInsetsAbsolute(binding.toolbar.getContentInsetEnd(), 200); //here 200 is set for padding left
        actionBarDrawerToggle.isDrawerSlideAnimationEnabled
        binding!!.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        binding!!.navView.setNavigationItemSelectedListener(this)
        binding!!.navView.bringToFront()


    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {


//        binding?.drawerLayout?.closeDrawer(GravityCompat.START)
//        var fragment = navHostFragment.childFragmentManager.fragments[0]
//        val id = item.itemId
//
//        if(id==R.id.dashboard){
//            navHostFragment.let { navFragment ->
//                navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
//
//                    if (fragment !is DashboardFragment)
//                        navHostFragment.navController.navigate(R.id.dashboardFragment)
//
//                }
//            }
//        }
//
//        else if (id == R.id.layout) {
//
//            navHostFragment.let { navFragment ->
//                navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
//
//                    if (fragment !is VentureListFragment) {
//                        navHostFragment.navController.navigate(R.id.plotsListFragment)
//                    }
//                }
//            }
//
//        } else if (id == R.id.logout) {
//            LogoutDialog()
//        }

        return true
    }

    override fun onClick(view: View) {

        /*  val fragment1: NavHostFragment? =
              supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
          var fragment: DashboardFragment =
              fragment1!!.childFragmentManager.fragments[0] as DashboardFragment
  */
        binding!!.drawerLayout.closeDrawer(GravityCompat.START)
        when (view.id) {
            R.id.layouts -> {
                navHostFragment.let { navFragment ->
                    navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->

                        if (fragment !is VentureListFragment)
                            navHostFragment.navController.navigate(R.id.plotsListFragment)

                    }
                }
            }
            R.id.dashboard -> {
                navHostFragment.let { navFragment ->
                    navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->

                        if (fragment !is DashboardFragment)
                            navHostFragment.navController.navigate(R.id.dashboardFragment)

                    }
                }
            }

            R.id.newrefferels -> {
                navHostFragment.let { navFragment ->
                    navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->

                        if (fragment !is NewReferral)
                            navHostFragment.navController.navigate(R.id.newReferral)

                    }
                }
            }


            R.id.nav_logout -> {

                LogoutDialog()
            }

        }

    }

    fun LogoutDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Do you want to logout?")
            .setPositiveButton(
                "Logout"
            ) { dialog: DialogInterface?, which: Int ->
                logout()
            }
            .setNegativeButton(
                "Cancel"
            ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
            .create().show()
    }

    fun logout() {

        this.let { SharedPref(it).getmSharedPrefInstance()?.clearSharedPref() }

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)
        this.finish()

    }


    private fun HeaderProfile() {
        try {
            val nav_user = hView!!.findViewById<TextView>(R.id.header_UserName)
            val name: String = SharedPref(applicationContext).getLoginUsrName().toString()


//            nav_user.setText(String.format( name));
            nav_user.text = String.format(name)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.dashboard_menu, menu)
//        menuItem = menu.findItem(R.id.logo_client)
//        menuItem?.setVisible(true)
//        menuItem.
//
//        return true
//    }


}

