package com.example.hirecruiterapp2.database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Recruiter(
    @SerializedName("recruiterId")
    @Expose
    val recruiterId: String,
    @SerializedName("recruiterName")
    @Expose
    val recruiterName: String,
    @SerializedName("userType")
    @Expose
    val userType: String,
    @SerializedName("recruiterEmail")
    @Expose
    val recruiterEmail: String?,
    @SerializedName("applications")
    @Expose
    val applications: List<ApplicationModel>? = null
)
