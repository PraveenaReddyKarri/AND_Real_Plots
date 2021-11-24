package com.example.kapilhomes.venturelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kapilhomes.servicesapi.RetrofitInstance
import com.example.kapilhomes.servicesapi.RetrofitInstanceTwo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class VentureListViewModel : ViewModel() {

    val response: MutableLiveData<Response<ArrayList<VentureListResponse>>> = MutableLiveData()



    fun getList() {
        viewModelScope.launch {
            try {
                response.postValue((RetrofitInstance.api.getVentureList()))
            } catch (exception: HttpException) {

            }
        }
    }
}