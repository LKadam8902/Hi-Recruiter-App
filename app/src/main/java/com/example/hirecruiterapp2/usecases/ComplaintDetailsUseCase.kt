package com.example.hirecruiterapp2.usecases

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hirecruiterapp2.database.Jobs
import com.example.hirecruiterapp2.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID

class ComplaintDetailsUseCase(
    private val userRepository: UserRepository
) {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var jobsLiveData = MutableLiveData<List<Jobs>>()
    var jobsInfoLiveData = MutableLiveData<Jobs>()

    fun setJobById(
        role: String,
        type: String,
        skillsHave: String,
        resume:String,
        date: String
    ) {
        val currentUser = mAuth.currentUser?.uid!!
        val complaintID = UUID.randomUUID().toString()
        val jobs = Jobs(complaintID, role, type, skillsHave,"","", resume,date)
        userRepository.addComplaintById(currentUser, jobs) {
            Log.d("Job Application", "Job Application passed")
        }
    }

    fun getAllJob(): MutableLiveData<List<Jobs>> {
        userRepository.getAllJobs().observeForever {
            jobsLiveData.postValue(it)
            Log.d("job use-case", "Reached use-case")
        }
        return jobsLiveData
    }

    fun getComplaintInfo(complaintId: String): MutableLiveData<Jobs> {
        userRepository.getComplaintInfo(complaintId) {
            jobsInfoLiveData.postValue(it)
        }
        return jobsInfoLiveData
    }


}