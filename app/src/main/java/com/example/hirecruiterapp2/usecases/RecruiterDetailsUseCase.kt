package com.example.hirecruiterapp2.usecases

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hirecruiterapp2.database.ApplicationModel
import com.example.hirecruiterapp2.database.Recruiter
import com.example.hirecruiterapp2.repository.RecruiterRepository
import com.google.firebase.auth.FirebaseAuth

class RecruiterDetailsUseCase(private val recruiterRepository: RecruiterRepository) {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var application = MutableLiveData<List<ApplicationModel>>()
    var recruiter = MutableLiveData<Recruiter>()

    fun setRecruiterData(name: String, email: String) {
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            val recruiter = Recruiter(currentUser.uid, name,"recruiter", email, null)
            recruiterRepository.addRecruiter(recruiter) {
                if (it != null) {
                    Log.d("add recruiter usecase", "success")
                } else {
                    Log.d("add recruiter usecase", "failed")
                }
            }

        } else {
            Log.d("add recruiter usecase", "recruiter not found")
        }
    }

    fun getRecruiterById(): MutableLiveData<Recruiter> {
        val currentUser = mAuth.currentUser!!.uid
        recruiterRepository.getRecruitertById(currentUser).observeForever {
            recruiter.postValue(it)
        }
        return recruiter
    }


}