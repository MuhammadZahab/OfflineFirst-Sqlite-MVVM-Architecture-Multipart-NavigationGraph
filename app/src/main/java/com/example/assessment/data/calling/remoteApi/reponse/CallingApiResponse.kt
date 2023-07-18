package com.example.assessment.data.calling.remoteApi.reponse

import com.google.gson.annotations.SerializedName

data class CallingApiResponse(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("number") val number : String
)
