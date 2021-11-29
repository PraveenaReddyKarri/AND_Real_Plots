package com.grgroup.kapilhomes.login

import com.google.gson.annotations.SerializedName

data class LoginRequest (


    @SerializedName("pUserName") var pUserName : String,
    @SerializedName("pPassword") var pPassword : String
        )