package com.grgroup.hexabuild.sitevisit

import com.google.gson.annotations.SerializedName

data class SiteVisitRequest (

//    @SerializedName("pContactType") var pContactType : String="Individual",
//    @SerializedName("pTitleName") var pTitleName : String,
//    @SerializedName("pName") var pName : String,
//    @SerializedName("pSurName") var pSurName : String,
//    @SerializedName("pBusinessEntitycontactNo") var pBusinessEntitycontactNo : String,
//    @SerializedName("pCreatedby") var pCreatedby : String,
//    @SerializedName("pStatusname") var pStatusname : String="Active",
//    @SerializedName("pContactName") var pContactName : String,
    @SerializedName("latitude") var latitude : String,
@SerializedName("longitude") var longitude : String,
@SerializedName("file_name") var file_name : String



)