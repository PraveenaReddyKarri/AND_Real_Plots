package com.grgroup.hexabuild.newreferal

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grgroup.hexabuild.servicesapi.RetrofitInstance
import com.grgroup.hexabuild.servicesapi.RetrofitInstanceTwo
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException
import retrofit2.Response

class NewReferralViewModel : ViewModel() {
    val response: MutableLiveData<Response<ArrayList<NewReferralResponse>>> = MutableLiveData()

    val data: MutableLiveData<Response<String>> = MutableLiveData()
    val pan: MutableLiveData<Response<String>> = MutableLiveData()
    val image: MutableLiveData<Response<ArrayList<String>>> = MutableLiveData()
    val titles: MutableLiveData<Response<ArrayList<TitlesResponse>>> = MutableLiveData()
    var imageBase64String: String? = null

    fun saveData(newReferralRequest: NewReferralRequest) {
        viewModelScope.launch{

            response.postValue(RetrofitInstanceTwo.api.newReferraldata(newReferralRequest))
        }
    }


    fun mobileValidation(mobileValidateRequest: MobileValidateRequest) {
        viewModelScope.launch {
            try {
                data.postValue((RetrofitInstanceTwo.api.mobileCheck(mobileValidateRequest)))
            } catch (exception: HttpException) {

            }
        }
    }


    fun getContactTitles(){
        viewModelScope.launch {
            try {
                titles.postValue((RetrofitInstanceTwo.api.getTitles()))
            } catch (exception: HttpException) {

            }
        }
    }


    fun panValidation(Plotno: String, LayoutId: String) {
        viewModelScope.launch {
            try {
                pan.postValue((RetrofitInstanceTwo.api.panCheck(Plotno, LayoutId)))
            } catch (exception: HttpException) {

            }
        }
    }


    fun imageUpload(file_path:MultipartBody.Part) {
        viewModelScope.launch {
            try {
                image.postValue((RetrofitInstanceTwo.api.imageUploading(file_path)))
            } catch (exception: HttpException) {
                Log.e("","")
            }
        }
    }

}