package com.example.hirecruiterapp2.fragments.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hirecruiterapp2.R
import com.example.hirecruiterapp2.databinding.FragmentOtpSignUpBinding
import com.example.hirecruiterapp2.switchFragmentUsecase
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OtpSignUpFragment : Fragment() {

    private lateinit var binding: FragmentOtpSignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var number: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.barOtpSignUp.visibility = View.INVISIBLE
        mAuth = FirebaseAuth.getInstance()
        fillForm()

    }

    private fun fillForm() {
        binding.btnGetOTP.setOnClickListener {
            binding.barOtpSignUp.visibility = View.VISIBLE
            if (binding.editNameTxt.toString().isEmpty()) {
                binding.barOtpSignUp.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), "Please enter your name", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (binding.editPhoneTxt.toString().isEmpty()) {
                    binding.barOtpSignUp.visibility = View.INVISIBLE
                    Toast.makeText(
                        requireActivity(),
                        "Please enter your email id",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    number = binding.editPhoneTxt.text.toString()
                    number = "+91$number"
                    otpSend(number)
                }
            }
        }

    }


    private fun otpSend(number: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.


            when (e) {
                is FirebaseAuthInvalidCredentialsException -> {
                    // Invalid request
                }

                is FirebaseTooManyRequestsException -> {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(activity, "Verification Failed: $e", Toast.LENGTH_SHORT).show()

                }

                is FirebaseAuthMissingActivityForRecaptchaException -> {
                    // reCAPTCHA verification attempted with null Activity
                    Toast.makeText(activity, "Verification Failed: $e", Toast.LENGTH_SHORT).show()
                }
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            binding.barOtpSignUp.visibility = View.INVISIBLE
            val bundle = Bundle()
            bundle.putString("name", binding.editNameTxt.text.toString())
            bundle.putString("number", number.trim())
            bundle.putString("OTP", verificationId)
            val verifyFragment = OtpVerifyFragment()
            val navigationUseCase =
                switchFragmentUsecase(requireActivity().supportFragmentManager, R.id.logFragment)
            navigationUseCase.navigateToFragment(verifyFragment, bundle)
        }
    }


}