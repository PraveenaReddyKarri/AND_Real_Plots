package com.grgroup.hexabuild.myreferals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grgroup.hexabuild.servicesapi.RetrofitInstanceTwo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class MyreferalsViewModel : ViewModel() {

    val referallist: MutableLiveData<Response<ArrayList<MyReferralResponse>>> = MutableLiveData()


    fun getReferalList( Rid: String) {
        viewModelScope.launch {
            try {
                referallist.postValue((RetrofitInstanceTwo.api.getCaderDetails(Rid)))
            } catch (exception: HttpException) {

            }
        }
    }


}