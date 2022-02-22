package com.grgroup.hexabuild.sitevisit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grgroup.hexabuild.newreferal.NewReferralRequest
import com.grgroup.hexabuild.newreferal.NewReferralResponse
import com.grgroup.hexabuild.servicesapi.RetrofitInstanceTwo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class SiteVisitingViewModel : ViewModel() {
    var imageBase64String: String? = null
//    val data: MutableLiveData<Response<String>> = MutableLiveData()
    val response: MutableLiveData<Response<ArrayList<SiteVisitResponse>>> = MutableLiveData()


    fun saveData(siteVisitRequest: SiteVisitRequest) {
        viewModelScope.launch{


            try {
                response.postValue(RetrofitInstanceTwo.api.saveuserData(siteVisitRequest))
            } catch (exception: HttpException) {

            }
        }
    }


}