package com.example.kapilhomes.servicesapi

import com.example.kapilhomes.layoutchart.LayoutChartResponse
import com.example.kapilhomes.login.GetUserIdResponse
import com.example.kapilhomes.login.LoginRequest
import com.example.kapilhomes.login.LoginResponse
import com.example.kapilhomes.newreferal.*
import com.example.kapilhomes.plotdetails.PlotDetailsResponse
import com.example.kapilhomes.venturelist.VentureListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ConnectTwo {



//    @GET("/api/HomesInventory/masters/PlotLayoutController/GetLandPurchasedLayoutdetailsby_landbankid")
//    suspend fun getVentureList(): Response<ArrayList<VentureListResponse>>
//
//    @GET("/api/HomesInventory/masters/PlotLayoutController/GetLayoutplotsdetailsbylayoutid")
//    suspend fun getlayoutchart(@Query("layoutid")layoutid: String): Response<ArrayList<LayoutChartResponse>>



//    @GET("/api/Plots/Masters/LayoutEntry/GetPlotnodetailsFromBranch")
//    suspend fun getplotDetails(@Query("Plotno")Plotno: String,@Query("LayoutId")LayoutId: String): Response<ArrayList<PlotDetailsResponse>>


    @POST("/api/login")
    suspend fun userLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("/api/Settings/Users/UserAccess/GetUserIDRefID")
    suspend fun getUsers(@Query("username")username: String): Response<ArrayList<GetUserIdResponse>>




    @POST("/api/loans/masters/contactmasterNew/Saveappcontact")
    suspend fun newReferraldata(@Body newReferralRequest: NewReferralRequest): Response<ArrayList<NewReferralResponse>>

    @POST("/api/loans/masters/contactmasterNew/GetPersonCount")
    suspend fun mobileCheck(@Body mobileValidateRequest: MobileValidateRequest): Response<String>

    @GET("/api/loans/masters/contactmasterNew/CheckDocumentExist")
    suspend fun panCheck(@Query("DocumentId")DocumentId: String,@Query("ReferenceNo")ReferenceNo: String): Response<String>


    //    8767666567
 @GET("api/Settings/getContacttitles")
    suspend fun getTitles(): Response<ArrayList<TitlesResponse>>

//    8466999733
}