package com.example.hirecruiterapp2.usecases

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hirecruiterapp2.database.Jobs
import com.example.hirecruiterapp2.database.SeekerModelItem
import com.example.hirecruiterapp2.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth


class UserDetailsUseCase(private val userRepository: UserRepository) {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var jobs = MutableLiveData<List<Jobs>>()
    var user = MutableLiveData<SeekerModelItem>()
    var meth = MutableLiveData<String>()

    fun getUserMethod(): MutableLiveData<String> {
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            mAuth.addAuthStateListener {
                if (currentUser.providerData.size > 0) {
                    meth.value =
                        currentUser.providerData[currentUser.providerData.size - 1].providerId
                } else {
                    Log.d("methodUsecase", "User not signed in")
                }
            }
        } else {
            mAuth.removeAuthStateListener {
                Log.d("methodUSer", "user signed out")
            }
        }
        return meth
    }

    fun setUserData(userId: String, name: String, email: String, phone: String) {
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            val providerData = currentUser.providerData
            if (providerData.isNotEmpty()) {
                val method = providerData[providerData.size - 1].providerId
                val user = when (method) {
                    "phone" -> SeekerModelItem(
                        userId,
                        name,
                        "seeker",
                        email,
                        phone,
                        "phone",
                        null
                    )

                    "google.com" -> SeekerModelItem(
                        userId,
                        name,
                        "seeker",
                        email,
                        null,
                        "google",
                        null
                    )

                    "password" -> SeekerModelItem(
                        userId,
                        name,
                        "seeker",
                        email,
                        null,
                        "email",
                        null
                    )

                    else -> null
                }
                user?.let {
                    userRepository.addUser(user) { addedUser ->
                        if (addedUser != null) {
                            Log.d("storeUserData", "User added successfully: $addedUser")
                        } else {
                            Log.e("storeUserData", "Error adding user")
                        }
                    }
                } ?: run {
                    Log.e(
                        "storeUserData",
                        "User creation failed due to unsupported authentication method: $method"
                    )
                }
            } else {
                Log.d("storeUserData", "No provider data found for the current user.")
            }
        } else {
            Log.d("storeUserData", "No current user found.")
        }

    }

    fun getUserById(): MutableLiveData<SeekerModelItem> {
        val currentUser = mAuth.currentUser!!.uid
        userRepository.getUserById(currentUser).observeForever {
            user.postValue(it)
        }
        return user
    }

}