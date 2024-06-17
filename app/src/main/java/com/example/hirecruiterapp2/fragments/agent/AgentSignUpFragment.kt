package com.example.hirecruiterapp2.fragments.agent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hirecruiterapp2.activities.AgentActivity
import com.example.hirecruiterapp2.databinding.FragmentAgentSignUpBinding
import com.example.hirecruiterapp2.viewmodel.RecruiterViewModel
import com.google.firebase.auth.FirebaseAuth

class AgentSignUpFragment : Fragment() {
private lateinit var binding: FragmentAgentSignUpBinding
private lateinit var mAuth: FirebaseAuth
private lateinit var name: String
private lateinit var email: String
private lateinit var password: String
private lateinit var recruiterViewModel: RecruiterViewModel

override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View {
    // Inflate the layout for this fragment
    mAuth = FirebaseAuth.getInstance()
    binding = FragmentAgentSignUpBinding.inflate(layoutInflater)
    return binding.root
}

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
     recruiterViewModel = ViewModelProvider(requireActivity())[RecruiterViewModel::class.java]
     binding.btnEmailSignUp.setOnClickListener {
         fillForm()
     }
}

 private fun fillForm() {
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
                         recruiterViewModel.addRecruiter(
                             name,
                             email,
                         )
                         val intent = Intent(requireActivity(), AgentActivity::class.java)
                         startActivity(intent)
                     } else {
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