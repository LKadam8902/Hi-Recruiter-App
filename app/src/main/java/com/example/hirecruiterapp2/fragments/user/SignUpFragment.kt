package com.example.hirecruiterapp2.fragments.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.hirecruiterapp2.activities.HomeActivity
import com.example.hirecruiterapp2.databinding.FragmentSignUpBinding
import com.example.hirecruiterapp2.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : Fragment() {


    private lateinit var binding: FragmentSignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var userViewModel: UserViewModel
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        binding.barEmailSignUp.visibility = View.INVISIBLE
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        fillForm()

    }

    private fun fillForm() {
        binding.btnEmailSignUp.setOnClickListener {
            binding.barEmailSignUp.visibility = View.VISIBLE
            if (binding.editNameTxt.text.toString().isEmpty()) {
                binding.barEmailSignUp.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), "Please enter name", Toast.LENGTH_SHORT).show()
            } else {
                if (binding.editEmailTxt.text.toString().isEmpty()) {
                    binding.barEmailSignUp.visibility = View.INVISIBLE
                    Toast.makeText(requireActivity(), "Please enter email id", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (binding.editPasswordTxt.text.toString().isEmpty()) {
                        binding.barEmailSignUp.visibility = View.INVISIBLE
                        Toast.makeText(
                            requireActivity(),
                            "Please enter password",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else if (binding.editPasswordTxt.text.toString().length < 8) {
                        binding.barEmailSignUp.visibility = View.INVISIBLE
                        Toast.makeText(
                            requireActivity(),
                            "Password must have more than 8 characters",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        name = binding.editNameTxt.text.toString()
                        email = binding.editEmailTxt.text.toString()
                        password = binding.editPasswordTxt.text.toString()
                        mAuth.createUserWithEmailAndPassword(
                            email,
                            password
                        ).addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                binding.barEmailSignUp.visibility = View.INVISIBLE
                                userViewModel.addUser(
                                    mAuth.currentUser!!.uid,
                                    name,
                                    email,
                                    ""
                                )
                                val intent = Intent(requireActivity(), HomeActivity::class.java)
                                FragmentManager.POP_BACK_STACK_INCLUSIVE
                                startActivity(intent)
                            } else {
                                binding.barEmailSignUp.visibility = View.INVISIBLE
                                Toast.makeText(
                                    requireActivity(),
                                    task.exception.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d("email-sign-up", task.exception.toString())
                            }
                        }
                    }
                }
            }
        }
    }


}