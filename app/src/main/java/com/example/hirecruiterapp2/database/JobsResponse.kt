package com.example.hirecruiterapp2.database

import com.google.gson.annotations.SerializedName

data class JobsResponse(
    @SerializedName("jobs") val jobs: List<Jobs>
)

