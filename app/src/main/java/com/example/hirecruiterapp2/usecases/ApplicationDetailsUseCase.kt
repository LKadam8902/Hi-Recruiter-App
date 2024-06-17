package com.example.hirecruiterapp2.usecases

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hirecruiterapp2.database.ApplicationModel
import com.example.hirecruiterapp2.database.Jobs
import com.example.hirecruiterapp2.repository.RecruiterRepository
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID

class ApplicationDetailsUseCase(
    private val recruiterRepository: RecruiterRepository
) {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var applicationLiveData = MutableLiveData<List<ApplicationModel>>()
    // var requestInfoLiveData = MutableLiveData<RequestModel>()
    val currentUser:String

    init{
        currentUser = mAuth.currentUser?.uid!!
    }

    fun setApplicationById(
        companyName: String,
        role: String,
        type: String,
        companypic: String,
        skillsNeeded: String,
        date: String
    ) {

        val applicationId = UUID.randomUUID().toString()
        val application =
            ApplicationModel(applicationId, companyName, role, type, companypic, skillsNeeded, date)
        recruiterRepository.addApplicationById(currentUser, application) {
            Log.d("Application", "Application passed")
        }
    }

    fun getAllApplication(): MutableLiveData<List<ApplicationModel>> {
        recruiterRepository.getAllApplications().observeForever {
            applicationLiveData.postValue(it)
            Log.d("application use-case", "Reached use-case")
        }
        return applicationLiveData
    }

    fun getApplicationById(): MutableLiveData<List<ApplicationModel>> {
        recruiterRepository.getApplicationsById(currentUser).observeForever {
            applicationLiveData.postValue(it)
            Log.d("application use-case", "Reached use-case")
        }
        return applicationLiveData
    }
}