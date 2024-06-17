package com.example.hirecruiterapp2.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.hirecruiterapp2.R
import com.example.hirecruiterapp2.fragments.UserTypeFragment
import com.example.hirecruiterapp2.viewmodel.RecruiterViewModel
import com.example.hirecruiterapp2.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        val fragment = supportFragmentManager.findFragmentById(R.id.logFragment)
        Log.d("TAG", fragment.toString())
        userViewModel=ViewModelProvider(this)[UserViewModel::class.java]
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            userViewModel.getUserById()
            userViewModel.observeUserById().observeForever {
                if(it.userType=="seeker"){
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(intent)
                    Log.d("Location", "Intent transition")
                }else{
                    val intent = Intent(this@MainActivity, AgentActivity::class.java)
                    startActivity(intent)
                    Log.d("Location", "Intent transition")
                }
            }

        } else {
            val userType = UserTypeFragment()
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.logFragment, userType)
                commit()
            }
        }
    }
}