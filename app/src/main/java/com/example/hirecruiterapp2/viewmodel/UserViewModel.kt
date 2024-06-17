package com.example.hirecruiterapp2.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hirecruiterapp2.database.SeekerModelItem
import com.example.hirecruiterapp2.repository.UserRepository
import com.example.hirecruiterapp2.usecases.UserDetailsUseCase

class UserViewModel : ViewModel() {
    private val repository: UserRepository = UserRepository()
    private val user: UserDetailsUseCase = UserDetailsUseCase(repository)
    var userLiveData = MutableLiveData<SeekerModelItem>()
    var userMethodLiveData = MutableLiveData<String>()

    fun getMethod() {
        user.getUserMethod().observeForever {
            userMethodLiveData.postValue(it)
        }
    }

    fun observeMethod(): MutableLiveData<String> {
        return userMethodLiveData
    }

    fun addUser(id: String, name: String, email: String?, number: String) {
        user.setUserData(id, name, email!!, number).apply {
            Log.d("addUser", "Reached viewmodel")
        }
    }

    fun getUserById() {
        user.getUserById().observeForever {
            userLiveData.postValue(it)
        }
    }

    fun observeUserById(): MutableLiveData<SeekerModelItem> {
        return userLiveData
    }

}
