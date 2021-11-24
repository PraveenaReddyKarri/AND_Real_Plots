package com.example.kapilhomes.layoutchart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kapilhomes.servicesapi.RetrofitInstance
import com.example.kapilhomes.servicesapi.RetrofitInstanceTwo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class LayoutChartViewModel : ViewModel() {

    val data: MutableLiveData<Response<ArrayList<LayoutChartResponse>>> = MutableLiveData()


    fun getChart(layoutid:String){

        viewModelScope.launch {
            try {
                data.postValue((RetrofitInstance.api.getlayoutchart(layoutid)))
            } catch (exception: HttpException) {

            }
        }
    }

}