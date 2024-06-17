package com.example.hirecruiterapp2.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hirecruiterapp2.database.ApplicationModel
import com.example.hirecruiterapp2.repository.RecruiterRepository
import com.example.hirecruiterapp2.usecases.ApplicationDetailsUseCase

class ApplicationViewModel : ViewModel() {
    var recruiterRepository = RecruiterRepository()
    private var applicationDetailsUseCase: ApplicationDetailsUseCase =
        ApplicationDetailsUseCase(recruiterRepository)
    var applicationsLiveData = MutableLiveData<List<ApplicationModel>>()
    var applicationsByIdLiveData = MutableLiveData<List<ApplicationModel>>()
    var jobsInfoLiveData = MutableLiveData<ApplicationViewModel>()


    fun addApplicationById(
        companyName: String,
        role: String,
        type: String,
        companypic: String,
        skillsNeeded: String,
        date: String
    ) {
        applicationDetailsUseCase.setApplicationById(
            companyName,
            role,
            type,
            companypic,
            skillsNeeded,
            date
        )
        Log.d("application vm", "application set")
    }

    fun getAllApplications() {
        applicationDetailsUseCase.getAllApplication().observeForever {
            applicationsLiveData.postValue(it)
        }
    }

    fun observeAllApplications(): MutableLiveData<List<ApplicationModel>> {
        return applicationsLiveData
    }

    fun getApplicationsById() {
        applicationDetailsUseCase.getApplicationById().observeForever {
            applicationsByIdLiveData.postValue(it)
        }
    }

    fun observeApplicationsById(): MutableLiveData<List<ApplicationModel>> {
        return applicationsByIdLiveData
    }


}