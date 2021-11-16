package com.example.kapilhomes.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kapilhomes.servicesapi.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel : ViewModel() {

    val response: MutableLiveData<Response<LoginResponse>> = MutableLiveData()

    fun doLogin(loginrequest:LoginRequest) {
    viewModelScope.launch{

        response.postValue(RetrofitInstance.api.userLogin(loginrequest))
    }
    }
}