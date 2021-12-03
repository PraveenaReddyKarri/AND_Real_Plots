package com.grgroup.hexabuild.login

import com.google.gson.annotations.SerializedName

class LoginResponse(


    @SerializedName("pUserID") var pUserID : String,
    @SerializedName("pContactRefID") var pContactRefID : String,
    @SerializedName("pUserName") var pUserName : String,
    @SerializedName("pRoleid") var pRoleid : Int,
    @SerializedName("pRoleFunctionsCOunt") var pRoleFunctionsCOunt : Int,
    @SerializedName("pRoleName") var pRoleName : String,
    @SerializedName("pPassword") var pPassword : String,
    @SerializedName("pUserType") var pUserType : String,
    @SerializedName("pstatusid") var pstatusid : Int,
    @SerializedName("pCreatedby") var pCreatedby : Int,
    @SerializedName("pSaltKey") var pSaltKey : String,
    @SerializedName("pEmployeeName") var pEmployeeName : String,
    @SerializedName("pToken") var pToken : String,
    @SerializedName("pStatus") var pStatus : String,
    @SerializedName("pUserorDesignation") var pUserorDesignation : String,
    @SerializedName("pActiveorInactive") var pActiveorInactive : Boolean,
    @SerializedName("pRefreshToken") var pRefreshToken : String
)
