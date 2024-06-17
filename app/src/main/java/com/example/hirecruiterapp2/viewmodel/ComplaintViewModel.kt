package com.example.hirecruiterapp2.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hirecruiterapp2.database.Jobs
import com.example.hirecruiterapp2.repository.UserRepository
import com.example.hirecruiterapp2.usecases.ComplaintDetailsUseCase

class ComplaintViewModel : ViewModel() {
    var userRepository = UserRepository()
    private var complaintDetailsUseCase: ComplaintDetailsUseCase =
        ComplaintDetailsUseCase(userRepository)
    var jobsLiveData = MutableLiveData<List<Jobs>>()
    var jobsInfoLiveData = MutableLiveData<Jobs>()


    fun addComplaintById(
        title: String,
        type: String,
        skillsHave: String,
        resume: String,
        date: String
    ) {
        complaintDetailsUseCase.setJobById(title, type,skillsHave, resume, date)
        Log.d("job vm", "job set")
    }

    fun getAllJobs() {
        complaintDetailsUseCase.getAllJob().observeForever {
            jobsLiveData.postValue(it)
            Log.d("job vm", "reached vm")
        }
    }

    fun getComplaintInfo(complaintId: String) {
        complaintDetailsUseCase.getComplaintInfo(complaintId).observeForever {
            jobsInfoLiveData.postValue(it)
        }
    }


    fun observeAllJobs(): MutableLiveData<List<Jobs>> {
        return jobsLiveData
    }

    fun observeComplaintInfo(): MutableLiveData<Jobs> {
        return jobsInfoLiveData
    }


}