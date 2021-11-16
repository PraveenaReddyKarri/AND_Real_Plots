package com.example.kapilhomes.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPref(private val context: Context) {


    protected val USER_NAME = "user_name"
    val PASSWORD1 by lazy { "password"}
    val PREF_NAME = "user_pref"
    val SESSION_KEY = "session_id"
    val USER_ID = "user_id"
    var ACCESS_KEY = "access_token"


    var mSharedPref: SharedPref? = null
    private var sharedPreferences: SharedPreferences? = null


    init {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    }

    /*  constructor(context: Context) : this() {
          sharedPreferences=context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
      }*/


    fun getmSharedPrefInstance(): SharedPref? {
        if (mSharedPref == null) mSharedPref = SharedPref(context)
        return mSharedPref
    }

    fun removeAccessToken() {
        val edit = sharedPreferences!!.edit()
        edit.remove(ACCESS_KEY).apply()
    }

    fun clearSharedPref() {
        val editor = sharedPreferences!!.edit()
        editor.clear()
        editor.apply()
    }

    fun setSessionId(sessionId: String?) {
        val editor = sharedPreferences!!.edit()
        editor.putString(SESSION_KEY, sessionId)
        editor.apply()
    }

    fun getSessionId(): String? {
        return sharedPreferences!!.getString(SESSION_KEY, "")
    }

    fun setUserId(userid: String?) {
        val editor = sharedPreferences!!.edit()
        editor.putString(USER_ID, userid.toString())
        editor.apply()
    }

    fun getUserId(): String? {
        return sharedPreferences!!.getString(USER_ID, "")
    }


    fun setLoginUserName(UserName: String?) {
        val editor = sharedPreferences!!.edit()
        editor.putString(USER_NAME, UserName)
        editor.apply()
    }

    fun getLoginUsrName(): String? {
        return sharedPreferences!!.getString(USER_NAME, "")
    }

//    fun setPassword(UserName: String?) {
//        val editor = sharedPreferences!!.edit()
//        editor.putString(PASSWORD, UserName)
//        editor.apply()
//    }

    fun getPassword(): String? {
        return sharedPreferences!!.getString(USER_NAME, "")
    }

    fun setValue(key: String, value: String) {
        val editor = sharedPreferences!!.edit()
        editor.putString(key, value)
        editor.apply()
    }

}