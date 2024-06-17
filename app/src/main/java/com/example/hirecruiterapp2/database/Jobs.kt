package com.example.hirecruiterapp2.database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Jobs(
    @SerializedName("JobId")
    @Expose
    val JobId: String,
    @SerializedName("role")
    @Expose
    val role: String,
    @SerializedName("type")
    @Expose
    val type: String,
    @SerializedName("skillsHave")
    @Expose
    val skillsHave: String,
    @SerializedName("recruiterId")
    @Expose
    val recruiterId:String?=null,
    @SerializedName("recruiterName")
    @Expose
    val recruiterName:String?=null,
    @SerializedName("resume")
    @Expose
    val resume:String,
    @SerializedName("date")
    @Expose
    val date: String
)