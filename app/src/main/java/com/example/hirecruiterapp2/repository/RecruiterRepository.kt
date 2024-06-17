package com.example.hirecruiterapp2.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hirecruiterapp2.database.ApplicationModel
import com.example.hirecruiterapp2.database.ApplicationResponse
import com.example.hirecruiterapp2.database.Recruiter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecruiterRepository {
    private val recruiter = MutableLiveData<Recruiter>()
    private val applications = MutableLiveData<List<ApplicationModel>>()

    fun addRecruiter(recruiter: Recruiter, callback: (Recruiter?) -> Unit) {
        RetrofitRecruiterInstance.recruiterApi.setRecruiterData(recruiter)
            .enqueue(object : Callback<Recruiter> {
                override fun onResponse(
                    call: Call<Recruiter>,
                    response: Response<Recruiter>
                ) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<Recruiter>, t: Throwable) {
                    callback(null)
                }
            })
        Log.d("addRecruiter", "Reached repo")
    }

    fun addApplicationById(
        recruiterId: String,
        application: ApplicationModel,
        callback: (Recruiter) -> Unit
    ) {
        RetrofitRecruiterInstance.recruiterApi.setApplicationData(application, recruiterId)
            .enqueue(object : Callback<Recruiter> {
                override fun onResponse(call: Call<Recruiter>, response: Response<Recruiter>) {
                    if (response.isSuccessful) {
                        callback(response.body()!!)
                    } else {
                        Log.d("addApplication", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<Recruiter>, t: Throwable) {
                    Log.d("addApplicationFail", "Error setting application")
                }

            })
    }

    fun getRecruitertById(recruiterId: String): MutableLiveData<Recruiter> {
        RetrofitRecruiterInstance.recruiterApi.getRecruiterById(recruiterId)
            .enqueue(object : Callback<Recruiter> {
                override fun onResponse(call: Call<Recruiter>, response: Response<Recruiter>) {
                    if (response.isSuccessful) {
                        recruiter.postValue(response.body())
                        Log.d("user repo ", "fetched user ${response.body()}")
                    } else {
                        Log.d("Response repo error", "unable to fetch agent data")
                    }
                }

                override fun onFailure(call: Call<Recruiter>, t: Throwable) {
                    Log.d("repo error", "unable to fetch agent data", t)
                }
            })
        return recruiter
    }

    fun getAllApplications(): MutableLiveData<List<ApplicationModel>> {
        RetrofitRecruiterInstance.recruiterApi.getAllApplications()
            .enqueue(object : Callback<List<ApplicationResponse>> {
                override fun onResponse(
                    call: Call<List<ApplicationResponse>>,
                    response: Response<List<ApplicationResponse>>
                ) {
                    if (response.isSuccessful) {
                        val allApplications = mutableListOf<ApplicationModel>()
                        response.body()?.forEach { applicationsResponse ->
                            allApplications.addAll(applicationsResponse.applicationList)
                        }
                        applications.postValue(allApplications)
                        Log.d("recruiter repo", "fetched application: ${allApplications}")
                    } else {
                        Log.d("recruiter repo error", "unable to fetch applications")
                    }
                }

                override fun onFailure(call: Call<List<ApplicationResponse>>, t: Throwable) {
                    Log.d("Repo failure", "unable to fetch")
                }
            })
        return applications
    }

    fun getApplicationsById(recruiterId: String): MutableLiveData<List<ApplicationModel>> {
        RetrofitRecruiterInstance.recruiterApi.getApplicationById(recruiterId)
            .enqueue(object : Callback<List<ApplicationResponse>> {
                override fun onResponse(
                    call: Call<List<ApplicationResponse>>,
                    response: Response<List<ApplicationResponse>>
                ) {
                    if (response.isSuccessful) {
                        val allApplications = mutableListOf<ApplicationModel>()
                        response.body()?.forEach { applicationsResponse ->
                            allApplications.addAll(applicationsResponse.applicationList)
                        }
                        applications.postValue(allApplications)
                        Log.d("recruiter repo", "fetched application: ${allApplications}")
                    } else {
                        Log.d("recruiter repo error", "unable to fetch applications")
                    }
                }

                override fun onFailure(call: Call<List<ApplicationResponse>>, t: Throwable) {
                    Log.d("Repo failure", "unable to fetch")
                }
            })
        return applications
    }




}