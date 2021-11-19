package com.example.kapilhomes.newreferal

import com.google.gson.annotations.SerializedName

class TitlesResponse(


    @SerializedName("pCountry") var pCountry : String,
    @SerializedName("pCountryId") var pCountryId : Int,
    @SerializedName("pState") var pState : String,
    @SerializedName("pStateId") var pStateId : Int,
    @SerializedName("pDistrict") var pDistrict : String,
    @SerializedName("pDistrictId") var pDistrictId : Int,
    @SerializedName("pTitleName") var pTitleName : String,
    @SerializedName("pEnterprisecode") var pEnterprisecode : String,
    @SerializedName("pBranchcode") var pBranchcode : String,
    @SerializedName("pContacttype") var pContacttype : String,
    @SerializedName("pApplicanttype") var pApplicanttype : String,
    @SerializedName("id") var id : String,
    @SerializedName("text") var text : String,
    @SerializedName("createdby") var createdby : Int,
    @SerializedName("modifiedby") var modifiedby : Int,
    @SerializedName("pCreatedby") var pCreatedby : Int,
    @SerializedName("pModifiedby") var pModifiedby : Int,
    @SerializedName("pStatusid") var pStatusid : String,
    @SerializedName("pStatusname") var pStatusname : String,
    @SerializedName("pEffectfromdate") var pEffectfromdate : String,
    @SerializedName("pEffecttodate") var pEffecttodate : String,
    @SerializedName("ptypeofoperation") var ptypeofoperation : String
)
