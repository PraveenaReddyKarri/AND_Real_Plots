package com.example.kapilhomes.servicesapi

import com.example.kapilhomes.layoutchart.LayoutChartResponse
import com.example.kapilhomes.login.LoginRequest
import com.example.kapilhomes.login.LoginResponse
import com.example.kapilhomes.plotdetails.PlotDetailsResponse
import com.example.kapilhomes.venturelist.VentureListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Connect {


    //central
    @GET("/api/HomesInventory/masters/PlotLayoutController/GetLandPurchasedLayoutdetailsby_landbankid")
    suspend fun getVentureList(): Response<ArrayList<VentureListResponse>>

    @GET("/api/HomesInventory/masters/PlotLayoutController/GetLayoutplotsdetailsbylayoutid")
    suspend fun getlayoutchart(@Query("layoutid")layoutid: String): Response<ArrayList<LayoutChartResponse>>


    //central
    @GET("/api/Plots/Masters/LayoutEntry/GetPlotnodetailsFromBranch")
    suspend fun getplotDetails(@Query("Plotno")Plotno: String,@Query("LayoutId")LayoutId: String): Response<ArrayList<PlotDetailsResponse>>

//both
    @POST("/api/login")
    suspend fun userLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>


}