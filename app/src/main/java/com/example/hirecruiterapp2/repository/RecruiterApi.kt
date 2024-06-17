package com.example.hirecruiterapp2.repository

import com.example.hirecruiterapp2.database.ApplicationModel
import com.example.hirecruiterapp2.database.ApplicationResponse
import com.example.hirecruiterapp2.database.Jobs
import com.example.hirecruiterapp2.database.Recruiter
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecruiterApi {

    @POST("addRecruiter")
    fun setRecruiterData(@Body agent: Recruiter): retrofit2.Call<Recruiter>

    @POST("addApplication/{id}")
    fun setApplicationData(
        @Body application: ApplicationModel,
        @Path("id") recruiterId: String
    ): retrofit2.Call<Recruiter>

    @GET("allApplications")
    fun getAllApplications(): retrofit2.Call<List<ApplicationResponse>>

    @GET("recruiter/{id}")
    fun getRecruiterById(@Path("id") recruiterId: String): retrofit2.Call<Recruiter>

    @GET("getApplication/{id}")
    fun getApplicationById(@Path("id") recruiterId: String):Call<List<ApplicationResponse>>

    @GET("request/{id}")
    fun getComplaintInfo(@Path("id") complaintId: String): retrofit2.Call<Jobs>
}