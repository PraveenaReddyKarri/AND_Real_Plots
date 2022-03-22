package com.grgroup.hexabuild.sitevisit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grgroup.hexabuild.servicesapi.RetrofitInstanceTwo
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException
import retrofit2.Response

class SiteVisitingViewModel : ViewModel() {
    var imageBase64String: String? = null

    //    val data: MutableLiveData<Response<String>> = MutableLiveData()
    val response: MutableLiveData<Response<String>> = MutableLiveData()

    val image: MutableLiveData<Response<ArrayList<String>>> = MutableLiveData()

    fun saveData(siteVisitRequest: SiteVisitRequest) {
        viewModelScope.launch {


            try {
                response.postValue(RetrofitInstanceTwo.api.saveuserData(siteVisitRequest))
            } catch (exception: HttpException) {

            }
        }
    }

    fun imageUpload(parts: List<MultipartBody.Part>) {
        viewModelScope.launch {
            try {
                image.postValue((RetrofitInstanceTwo.api.multiIimaeUploading(parts)))
            } catch (exception: HttpException) {
                Log.e("", "")
            }
        }


    }
}