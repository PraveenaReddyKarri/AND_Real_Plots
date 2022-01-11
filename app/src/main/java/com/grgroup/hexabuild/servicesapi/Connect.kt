package com.grgroup.hexabuild.servicesapi

import com.grgroup.hexabuild.layoutchart.LayoutChartResponse
import com.grgroup.hexabuild.plotdetails.PlotDetailsResponse
import com.grgroup.hexabuild.venturelist.VentureListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Connect {


//TESTING//
//    @GET("/api/HomesInventory/masters/PlotLayoutController/GetLandPurchasedLayoutdetailsby_landbankid")
//    suspend fun getVentureList(): Response<ArrayList<VentureListResponse>>
//
//    @GET("/api/HomesInventory/masters/PlotLayoutController/GetLayoutplotsdetailsbylayoutid")
//    suspend fun getlayoutchart(@Query("layoutid")layoutid: String): Response<ArrayList<LayoutChartResponse>>
//
//
//
//    @GET("/api/Plots/Masters/LayoutEntry/GetPlotnodetailsFromBranch")
//    suspend fun getplotDetails(@Query("Plotno")Plotno: String,@Query("LayoutId")LayoutId: String): Response<ArrayList<PlotDetailsResponse>>

    //TESTING//




//LIVE

    @GET("http://13.234.192.218:65446/api/HomesInventory/masters/PlotLayoutController/GetLandPurchasedLayoutdetailsby_landbankid")
    suspend fun getVentureList(): Response<ArrayList<VentureListResponse>>

    @GET("/api/HomesInventory/masters/PlotLayoutController/GetLayoutplotsdetailsbylayoutid")
    suspend fun getlayoutchart(@Query("layoutid")layoutid: String): Response<ArrayList<LayoutChartResponse>>



    @GET("/api/Plots/Masters/LayoutEntry/GetPlotnodetailsFromBranch")
    suspend fun getplotDetails(@Query("Plotno")Plotno: String,@Query("LayoutId")LayoutId: String): Response<ArrayList<PlotDetailsResponse>>

//LIVE















//    @POST("/api/login")
//    suspend fun userLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>
//
//    @GET("/api/Settings/Users/UserAccess/GetUserIDRefID")
//    suspend fun getUsers(@Query("username")username: String): Response<ArrayList<GetUserIdResponse>>
//
//
//
//
//    @POST("api/loans/masters/contactmasterNew/Saveappcontact")
//    suspend fun newReferraldata(@Body newReferralRequest: NewReferralRequest): Response<ArrayList<NewReferralResponse>>
//
//    @POST("api/loans/masters/contactmasterNew/GetPersonCount")
//    suspend fun mobileCheck(@Body mobileValidateRequest: MobileValidateRequest): Response<String>
//
////    8767666567
// @GET("api/Settings/getContacttitles")
//    suspend fun getTitles(): Response<ArrayList<TitlesResponse>>

//    8466999733
}