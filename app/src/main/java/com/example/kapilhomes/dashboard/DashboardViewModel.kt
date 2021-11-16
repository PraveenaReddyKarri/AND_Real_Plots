package com.example.kapilhomes.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kapilhomes.login.LoginResponse
import retrofit2.Response

class DashboardViewModel : ViewModel() {

    val data: MutableLiveData<Dasboarddata> = MutableLiveData()

}