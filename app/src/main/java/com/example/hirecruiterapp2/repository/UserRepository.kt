package com.example.hirecruiterapp2.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hirecruiterapp2.database.Jobs
import com.example.hirecruiterapp2.database.JobsResponse
import com.example.hirecruiterapp2.database.SeekerModelItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    private val user = MutableLiveData<SeekerModelItem>()
    private val jobs = MutableLiveData<List<Jobs>>()

    fun addUser(user: SeekerModelItem, callback: (SeekerModelItem?) -> Unit) {
        RetrofitSeekerInstance.seekerApi.setSeekerData(user)
            .enqueue(object : Callback<SeekerModelItem> {
                override fun onResponse(
                    call: Call<SeekerModelItem>,
                    response: Response<SeekerModelItem>
                ) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<SeekerModelItem>, t: Throwable) {
                    callback(null)
                }
            })
        Log.d("addSeeker", "Reached repo")
    }

    fun addComplaintById(userId: String, jobs: Jobs, callback: (SeekerModelItem) -> Unit) {
        RetrofitSeekerInstance.seekerApi.setJobData(jobs, userId)
            .enqueue(object : Callback<SeekerModelItem> {
                override fun onResponse(
                    call: Call<SeekerModelItem>,
                    response: Response<SeekerModelItem>
                ) {
                    if (response.isSuccessful) {
                        callback(response.body()!!)
                    } else {
                        Log.d("addJob", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<SeekerModelItem>, t: Throwable) {
                    Log.d("addJobFail", "Error setting job")
                }

            })
    }

    fun getUserById(userId: String): MutableLiveData<SeekerModelItem> {
        RetrofitSeekerInstance.seekerApi.getSeekerById(userId)
            .enqueue(object : Callback<SeekerModelItem> {
                override fun onResponse(
                    call: Call<SeekerModelItem>,
                    response: Response<SeekerModelItem>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                        Log.d("user repo ", "fetched user ${response.body()}")
                    } else {
                        Log.d("Response repo error", "unable to fetch user data")
                    }
                }

                override fun onFailure(call: Call<SeekerModelItem>, t: Throwable) {
                    Log.d("repo error", "unable to fetch user data", t)
                }
            })
        return user
    }

    fun getAllJobs(): MutableLiveData<List<Jobs>> {
        RetrofitSeekerInstance.seekerApi.getAllJobs()
            .enqueue(object : Callback<List<JobsResponse>> {
                override fun onResponse(
                    call: Call<List<JobsResponse>>,
                    response: Response<List<JobsResponse>>
                ) {
                    if (response.isSuccessful) {
                        val allJobs = mutableListOf<Jobs>()
                        response.body()?.forEach { jobsResponse ->
                            allJobs.addAll(jobsResponse.jobs)
                        }
                        jobs.postValue(allJobs)
                        Log.d("user repo", "fetched jobs: ${allJobs}")
                    } else {
                        Log.d("user repo error", "unable to fetch jobs")
                    }
                }

                override fun onFailure(call: Call<List<JobsResponse>>, t: Throwable) {
                    Log.d("Repo failure", "unable to fetch")
                }
            })
        return jobs
    }

    fun getComplaintInfo(complaintId: String, callback: (Jobs) -> Unit) {
        RetrofitSeekerInstance.seekerApi.getJobInfo(complaintId)
            .enqueue(object : Callback<Jobs> {
                override fun onResponse(call: Call<Jobs>, response: Response<Jobs>) {
                    if (response.isSuccessful) {
                        callback(response.body()!!)
                    } else {
                        Log.d("Complaint Repo", "Complaint Repo not instiated")
                    }
                }

                override fun onFailure(call: Call<Jobs>, t: Throwable) {
                    Log.d("Complaint Repo", "Complaint Repo Failed")
                }

            })
    }

}