package com.grgroup.hexabuild.sitevisit

import com.google.gson.annotations.SerializedName

class SiteVisitResponse(


    @SerializedName("pstatus") var pstatus : String,
    @SerializedName("pcid") var pcid : String
)
