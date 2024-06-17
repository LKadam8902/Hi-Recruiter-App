package com.example.hirecruiterapp2.fragments.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hirecruiterapp2.activities.HomeActivity
import com.example.hirecruiterapp2.databinding.FragmentOtpVerifyBinding
import com.example.hirecruiterapp2.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class OtpVerifyFragment : Fragment() {

    private lateinit var binding: FragmentOtpVerifyBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().reference.child("Users")
        binding = FragmentOtpVerifyBinding.inflate(layoutInflater)
        userViewModel = activity?.let { ViewModelProvider(it)[UserViewModel::class.java] }!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.barOtpVerify.visibility = View.INVISIBLE
        val name = arguments?.getString("name").toString()
        val number = arguments?.getString("number").toString()
        val verificationId = arguments?.getString("OTP")

        binding.numberTxt.text = number



        binding.btnOtp.setOnClickListener {
            binding.barOtpVerify.visibility = View.VISIBLE
            if (binding.editOtpTxt.text.isNullOrEmpty()) {
                binding.barOtpVerify.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), "Please enter OTP", Toast.LENGTH_SHORT).show()
            } else {
                val credential = PhoneAuthProvider.getCredential(
                    verificationId!!,
                    binding.editOtpTxt.text.toString()
                )
                signInWithPhoneAuthCredential(credential, name, "", number)
            }
        }

    }

    private fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        name: String,
        email: String,
        number: String,
    ) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    binding.barOtpVerify.visibility = View.INVISIBLE
                    // Sign in success, update UI with the signed-in user's information
                    // mAuth.currentUser?.verifyBeforeUpdateEmail(email)
                    userViewModel.addUser(
                        mAuth.currentUser?.uid.toString(),
                        name,
                        email,
                        number
                    )
                    //PhoneAuthProvider.getCredential()
                    // mAuth.createUserWithEmailAndPassword(email,password)
                    val intent = Intent(requireActivity(), HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    binding.barOtpVerify.visibility = View.INVISIBLE
                    Toast.makeText(requireActivity(), "OTP is invalid", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
