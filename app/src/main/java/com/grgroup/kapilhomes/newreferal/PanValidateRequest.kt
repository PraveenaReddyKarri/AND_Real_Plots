package com.grgroup.kapilhomes.newreferal

import com.google.gson.annotations.SerializedName

data class PanValidateRequest (


    @SerializedName("DocumentId") var DocumentId : String,
    @SerializedName("ReferenceNo") var ReferenceNo : String

        )