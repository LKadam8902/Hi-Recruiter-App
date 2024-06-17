package com.example.hirecruiterapp2.database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApplicationModel(
    @SerializedName("applicationId")
    @Expose
    val applicationId: String,
    @SerializedName("company")
    @Expose
    val company: String,
    @SerializedName("role")
    @Expose
    val role: String,
    @SerializedName("type")
    @Expose
    val type: String,
    @SerializedName("companypic")
    @Expose
    val companyPic: String,
    @SerializedName("skillsNeeded")
    @Expose
    val skillsNeeded: String,
    @SerializedName("datePosted")
    @Expose
    val datePosted: String
)