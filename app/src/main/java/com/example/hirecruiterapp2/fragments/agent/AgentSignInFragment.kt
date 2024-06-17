package com.example.hirecruiterapp2.fragments.agent

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hirecruiterapp2.R
import com.example.hirecruiterapp2.activities.AgentActivity
import com.example.hirecruiterapp2.databinding.FragmentAgentSignInBinding
import com.example.hirecruiterapp2.switchFragmentUsecase
import com.google.firebase.auth.FirebaseAuth


class AgentSignInFragment : Fragment() {
    private lateinit var binding: FragmentAgentSignInBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance()
        binding = FragmentAgentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.setOnClickListener {
            fillForm()
        }

        binding.btnSignUp.setOnClickListener {
            val agentSignUp = AgentSignUpFragment()
            val navigationUseCase =
                switchFragmentUsecase(requireActivity().supportFragmentManager, R.id.logFragment)
            navigationUseCase.navigateToFragment(agentSignUp)
        }
    }

    private fun fillForm() {

        if (binding.editEmailTxt.toString().isEmpty()) {
            Toast.makeText(requireActivity(), "Please enter your email id", Toast.LENGTH_SHORT)
                .show()

        } else {
            if (binding.editPasswordTxt.toString().isEmpty()) {
                Toast.makeText(
                    requireActivity(),
                    "Please enter your password",
                    Toast.LENGTH_SHORT
                )
                    .show()

            } else {
                signInWithEmailAndPassword(
                    binding.editEmailTxt.text.toString(),
                    binding.editPasswordTxt.text.toString()
                )
            }

        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(requireActivity(), AgentActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Invalid email/password",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

}