package com.grgroup.hexabuild.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPref(private val context: Context) {


    protected val USER_NAME = "user_name"
    protected val CREATEDBY_ID = "createdby_id"
    protected val INTRODUCED_ID = "introduced_id"
    protected val CONTACT_ID = "contact_id"
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

    fun setAccessKey(accessKey: String?) {
        val editor = sharedPreferences!!.edit()
        editor.putString(ACCESS_KEY, accessKey)
        editor.apply()
    }

    fun getAccessKey(): String? {
        return sharedPreferences!!.getString(ACCESS_KEY, "")
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

    fun  setCreatedbyId(CreatedId: String?){
        val editor = sharedPreferences!!.edit()
        editor.putString(CREATEDBY_ID, CreatedId)
        editor.apply()
    }

    fun getCreatedbyId(): String? {
        return sharedPreferences!!.getString(CREATEDBY_ID, "")
    }


    fun  setIntrocudedId(CreatedId: String?){
        val editor = sharedPreferences!!.edit()
        editor.putString(INTRODUCED_ID, CreatedId)
        editor.apply()
    }

    fun getIntrocudedId(): String? {
        return sharedPreferences!!.getString(INTRODUCED_ID, "")
    }


    fun  setContactid(Contactid: String?){
        val editor = sharedPreferences!!.edit()
        editor.putString(CONTACT_ID, Contactid)
        editor.apply()
    }

    fun getContactid(): String? {
        return sharedPreferences!!.getString(CONTACT_ID, "")
    }


//    fun setPassword(UserName: String?) {
//        val editor = sharedPreferences!!.edit()
//        editor.putString(PASSWORD, UserName)
//        editor.apply()
//    }



    fun setValue(key: String, value: String) {
        val editor = sharedPreferences!!.edit()
        editor.putString(key, value)
        editor.apply()
    }

}