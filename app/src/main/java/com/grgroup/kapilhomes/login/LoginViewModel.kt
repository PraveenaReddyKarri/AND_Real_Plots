package com.grgroup.kapilhomes.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grgroup.kapilhomes.servicesapi.RetrofitInstanceTwo
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel : ViewModel() {

    val response: MutableLiveData<Response<LoginResponse>> = MutableLiveData()
    val data: MutableLiveData<Response<ArrayList<GetUserIdResponse>>> = MutableLiveData()

    fun doLogin(loginrequest:LoginRequest) {
    viewModelScope.launch{

        response.postValue(RetrofitInstanceTwo.api.userLogin(loginrequest))
    }
    }


    fun getId(username: String) {
        viewModelScope.launch{

            data.postValue(RetrofitInstanceTwo.api.getUsers(username))
        }
    }
}