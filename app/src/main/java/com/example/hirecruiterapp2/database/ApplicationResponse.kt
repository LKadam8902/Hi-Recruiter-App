package com.example.hirecruiterapp2.database

import com.google.gson.annotations.SerializedName

data class ApplicationResponse(
    @SerializedName("applications") val applicationList: List<ApplicationModel>
)
