package com.example.hirecruiterapp2.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hirecruiterapp2.database.Recruiter
import com.example.hirecruiterapp2.repository.RecruiterRepository
import com.example.hirecruiterapp2.usecases.RecruiterDetailsUseCase

class RecruiterViewModel : ViewModel() {
    private val repository: RecruiterRepository = RecruiterRepository()
    private val recruiter: RecruiterDetailsUseCase = RecruiterDetailsUseCase(repository)
    private var recruiterLiveData = MutableLiveData<Recruiter>()

    fun addRecruiter(name: String, email: String?) {
        recruiter.setRecruiterData(name, email!!).apply {
            Log.d("addRecruiter", "Reached viewmodel")
        }
    }

    fun getRecruiterById() {
        recruiter.getRecruiterById().observeForever {
            recruiterLiveData.postValue(it)
        }
    }

    fun observeRecruiterById(): MutableLiveData<Recruiter> {
        return recruiterLiveData
    }

}