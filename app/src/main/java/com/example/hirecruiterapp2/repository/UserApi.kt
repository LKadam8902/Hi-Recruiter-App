package com.example.hirecruiterapp2.repository

import com.example.hirecruiterapp2.database.Jobs
import com.example.hirecruiterapp2.database.JobsResponse
import com.example.hirecruiterapp2.database.SeekerModelItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface SeekerApi {

    @POST("addSeeker")
    fun setSeekerData(@Body seeker: SeekerModelItem): Call<SeekerModelItem>

    @POST("addJob/{id}")
    fun setJobData(
        @Body jobs: Jobs,
        @Path("id") seekerId: String
    ): Call<SeekerModelItem>

    @GET("getAllJobs")
    fun getAllJobs(): Call<List<JobsResponse>>

    @GET("seeker/{id}")
    fun getSeekerById(@Path("id") seekerId: String): Call<SeekerModelItem>

    @GET("jobs/{id}")
    fun getJobInfo(@Path("id") jobId: String): Call<Jobs>

}