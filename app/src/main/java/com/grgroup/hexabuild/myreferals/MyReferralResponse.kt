package com.grgroup.hexabuild.myreferals

import com.google.gson.annotations.SerializedName

class MyReferralResponse(


    @SerializedName("referral_name") var referralName: String?,
    @SerializedName("tbl_mst_referral_id") var tblMstReferralId: Int?,
    @SerializedName("cadreid") var cadreid: Int?,
    @SerializedName("contact_id") var contactId: Int?,
    @SerializedName("cadrename") var cadrename: String?,
    @SerializedName("status") var status: String?,
    @SerializedName("introduced_id") var introducedId: String?,
    @SerializedName("referral_code") var referralCode: String?,
    @SerializedName("pan_number") var panNumber: String?,
    @SerializedName("tdssectionid") var tdssectionid: Int?,
    @SerializedName("transaction_date") var transactionDate: String?,
    @SerializedName("contactname") var contactname: String?,
    @SerializedName("referral_name_tree") var referralNameTree: String?


) {
    fun getName(): String? {
        if (referralNameTree.toString().equals("")) {
            return contactname
        } else {
            return referralNameTree
        }
    }
}
