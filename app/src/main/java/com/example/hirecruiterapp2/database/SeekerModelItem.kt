package com.example.hirecruiterapp2.database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SeekerModelItem(
    @SerializedName("seekerId")
    @Expose
    val seekerId: String,
    @SerializedName("seekerName")
    @Expose
    val seekerName: String,
    @SerializedName("userType")
    @Expose
    val userType: String,
    @SerializedName("seekerEmail")
    @Expose
    val seekerEmail: String? = null,
    @SerializedName("seekerPhone")
    @Expose
    val seekerPhone: String? = null,
    @SerializedName("seekerMethod")
    @Expose
    val seekerMethod: String,
    @SerializedName("jobs")
    @Expose
    val seekJobs: List<String>? = null
)