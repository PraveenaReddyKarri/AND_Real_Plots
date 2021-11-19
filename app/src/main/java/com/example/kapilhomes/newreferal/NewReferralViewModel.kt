package com.example.kapilhomes.newreferal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kapilhomes.servicesapi.RetrofitInstance
import com.example.kapilhomes.servicesapi.RetrofitInstanceTwo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class NewReferralViewModel : ViewModel() {
    val response: MutableLiveData<Response<ArrayList<NewReferralResponse>>> = MutableLiveData()

    val data: MutableLiveData<Response<String>> = MutableLiveData()
    val pan: MutableLiveData<Response<String>> = MutableLiveData()
    val titles: MutableLiveData<Response<ArrayList<TitlesResponse>>> = MutableLiveData()


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


    fun panValidation(panValidateRequest: PanValidateRequest) {
        viewModelScope.launch {
            try {
                pan.postValue((RetrofitInstanceTwo.api.panCheck(panValidateRequest)))
            } catch (exception: HttpException) {

            }
        }
    }

}