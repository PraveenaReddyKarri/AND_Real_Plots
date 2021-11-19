package com.example.kapilhomes.newreferal

import com.google.gson.annotations.SerializedName

class MobileValidateResponse(


    @SerializedName("pContactId") var pContactId : Int,
    @SerializedName("pcontactexistingstatus") var pcontactexistingstatus : Boolean,
    @SerializedName("pContactType") var pContactType : String,
    @SerializedName("pDob") var pDob : String,
    @SerializedName("pGender") var pGender : String,
    @SerializedName("pGenderCode") var pGenderCode : String,
    @SerializedName("pSpouseName") var pSpouseName : String,
    @SerializedName("pAddressList") var pAddressList : String,
    @SerializedName("pcontactdetailslist") var pcontactdetailslist : String,
    @SerializedName("pEmailidsList") var pEmailidsList : String,
    @SerializedName("pbusinessList") var pbusinessList : String,
    @SerializedName("pEnterpriseTypelist") var pEnterpriseTypelist : String,
    @SerializedName("pBusinesstypelist") var pBusinesstypelist : String,
    @SerializedName("documentstorelist") var documentstorelist : String,
    @SerializedName("referralbankdetailslist") var referralbankdetailslist : String,
    @SerializedName("pBusinesscontactreferenceid") var pBusinesscontactreferenceid : String,
    @SerializedName("pBusinesscontactName") var pBusinesscontactName : String,
    @SerializedName("pAge") var pAge : Int,
    @SerializedName("pBusinessContactlist") var pBusinessContactlist : String,
    @SerializedName("pBusinesstype") var pBusinesstype : String,
    @SerializedName("pEnterpriseType") var pEnterpriseType : String,
    @SerializedName("pBusinessEntityEmailid") var pBusinessEntityEmailid : String,
    @SerializedName("pBusinessEntityContactno") var pBusinessEntityContactno : String,
    @SerializedName("pEmailId2") var pEmailId2 : String,
    @SerializedName("pAlternativeNo") var pAlternativeNo : String,
    @SerializedName("pTitleName") var pTitleName : String,
    @SerializedName("pName") var pName : String,
    @SerializedName("pSurName") var pSurName : String,
    @SerializedName("pBusinessEntitycontactNo") var pBusinessEntitycontactNo : String,
    @SerializedName("pBusinessEntityEmailId") var pBusinessEntityEmailId : String,
    @SerializedName("pContactimagepath") var pContactimagepath : String,
    @SerializedName("pContactName") var pContactName : String,
    @SerializedName("pReferenceId") var pReferenceId : String,
    @SerializedName("pPhoto") var pPhoto : String,
    @SerializedName("pFatherName") var pFatherName : String,
    @SerializedName("pRoleid") var pRoleid : String,
    @SerializedName("pRolename") var pRolename : String,
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
