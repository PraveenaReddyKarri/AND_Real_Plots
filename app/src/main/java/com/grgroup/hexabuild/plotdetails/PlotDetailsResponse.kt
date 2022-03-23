package com.grgroup.hexabuild.plotdetails

import com.google.gson.annotations.SerializedName

data class PlotDetailsResponse(

    @SerializedName("pStandarduomarea") var pStandarduomarea : String,
    @SerializedName("pStandarduomareaname") var pStandarduomareaname : String,
    @SerializedName("pBookingstatus") var pBookingstatus : String,
    @SerializedName("pAreainsquareyards") var pAreainsquareyards : String,
    @SerializedName("pSalablearea") var pSalablearea : String,
    @SerializedName("pCommonarea") var pCommonarea : String,
    @SerializedName("pActualPlotcost") var pActualPlotcost : String,
    @SerializedName("psalableareauomname") var psalableareauomname : String,
    @SerializedName("pMembername") var pMembername : String,
    @SerializedName("pmembercode") var pmembercode : String,
    @SerializedName("pmobileno") var pmobileno : String,
    @SerializedName("preferralname") var preferralname : String,
    @SerializedName("pbooking_date") var pbookingDate : String,
    @SerializedName("total_receivable") var total_receivable : String,
    @SerializedName("deductions_receivable") var deductions_receivable : String,
    @SerializedName("additions_receivable") var additions_receivable : String,
    @SerializedName("charges") var charges : String,

)
