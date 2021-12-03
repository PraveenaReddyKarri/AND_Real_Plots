package com.grgroup.hexabuild.newreferal

import com.google.gson.annotations.SerializedName

data class NewReferralRequest (

    @SerializedName("pContactType") var pContactType : String="Individual",
    @SerializedName("pTitleName") var pTitleName : String,
    @SerializedName("pName") var pName : String,
    @SerializedName("pSurName") var pSurName : String,
    @SerializedName("pBusinessEntitycontactNo") var pBusinessEntitycontactNo : String,
    @SerializedName("pCreatedby") var pCreatedby : String,
    @SerializedName("pStatusname") var pStatusname : String="Active",
    @SerializedName("pContactName") var pContactName : String,
    @SerializedName("pPanNumber") var pPanNumber : String,
@SerializedName("pintroducedid") var pintroducedid : String



)