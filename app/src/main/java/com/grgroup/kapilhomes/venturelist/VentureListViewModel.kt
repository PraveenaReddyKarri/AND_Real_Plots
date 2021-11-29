package com.grgroup.kapilhomes.venturelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grgroup.kapilhomes.servicesapi.RetrofitInstance
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