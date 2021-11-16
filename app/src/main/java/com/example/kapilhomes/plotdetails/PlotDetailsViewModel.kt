package com.example.kapilhomes.plotdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kapilhomes.layoutchart.LayoutChartResponse
import com.example.kapilhomes.servicesapi.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class PlotDetailsViewModel : ViewModel() {

    val data: MutableLiveData<Response<ArrayList<PlotDetailsResponse>>> = MutableLiveData()

    fun getPlotDetails(plotno:String,layoutid:String){


        viewModelScope.launch {

            try {
                data.postValue((RetrofitInstance.api.getplotDetails(plotno,layoutid)))
            } catch (exception: HttpException) {

            }

        }
    }

}