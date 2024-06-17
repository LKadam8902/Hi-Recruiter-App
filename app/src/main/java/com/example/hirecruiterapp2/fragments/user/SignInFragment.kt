package com.example.hirecruiterapp2.fragments.user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.hirecruiterapp2.R
import com.example.hirecruiterapp2.activities.HomeActivity
import com.example.hirecruiterapp2.databinding.FragmentSignInBinding
import com.example.hirecruiterapp2.switchFragmentUsecase
import com.example.hirecruiterapp2.viewmodel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var userViewModel: UserViewModel

    //  private val RC_SIGN_IN = 9001 // Any unique request code
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()



        binding.barEmailSignIn.visibility = View.INVISIBLE
        // Configure Google Sign In options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.btnOtpSignIn.setOnClickListener {
            val otpSignUp = OtpSignUpFragment()
            val navigationUseCase =
                switchFragmentUsecase(requireActivity().supportFragmentManager, R.id.logFragment)
            navigationUseCase.navigateToFragment(otpSignUp)
        }
        // Set onClickListener for Google Sign In button
        binding.btnGSignIn.setOnClickListener {

            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)

        }
        binding.btnClickSignUp.setOnClickListener {
            val emailSignUp = SignUpFragment()
            val navigationUseCase =
                switchFragmentUsecase(requireActivity().supportFragmentManager, R.id.logFragment)
            navigationUseCase.navigateToFragment(emailSignUp)
        }
        fillForm()

    }

    private fun fillForm() {
        binding.btnSignIn.setOnClickListener {
            binding.barEmailSignIn.visibility = View.VISIBLE
            binding.btnGSignIn.visibility = View.INVISIBLE
            binding.btnOtpSignIn.visibility = View.INVISIBLE
            binding.btnForgotPassword.visibility = View.INVISIBLE
            binding.btnClickSignUp.visibility = View.INVISIBLE
            binding.txtOptionsOR.visibility = View.INVISIBLE
            if (binding.editEmailTxt.toString().isEmpty()) {
                Toast.makeText(requireActivity(), "Please enter your email id", Toast.LENGTH_SHORT)
                    .show()
                binding.barEmailSignIn.visibility = View.INVISIBLE
                binding.btnGSignIn.visibility = View.VISIBLE
                binding.btnOtpSignIn.visibility = View.VISIBLE
                binding.btnForgotPassword.visibility = View.VISIBLE
                binding.btnClickSignUp.visibility = View.VISIBLE
                binding.txtOptionsOR.visibility = View.VISIBLE
            } else {
                if (binding.editPasswordTxt.toString().isEmpty()) {
                    Toast.makeText(
                        requireActivity(),
                        "Please enter your password",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    binding.barEmailSignIn.visibility = View.INVISIBLE
                    binding.btnGSignIn.visibility = View.VISIBLE
                    binding.btnOtpSignIn.visibility = View.VISIBLE
                    binding.btnForgotPassword.visibility = View.VISIBLE
                    binding.btnClickSignUp.visibility = View.VISIBLE
                    binding.txtOptionsOR.visibility = View.VISIBLE
                } else {
                    signInWithEmailAndPassword(
                        binding.editEmailTxt.text.toString(),
                        binding.editPasswordTxt.text.toString()
                    )
                }

            }
        }

    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // binding.barEmailSignIn.visibility = View.INVISIBLE
                    val intent = Intent(requireActivity(), HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    binding.barEmailSignIn.visibility = View.INVISIBLE
                    binding.btnGSignIn.visibility = View.VISIBLE
                    binding.btnOtpSignIn.visibility = View.VISIBLE
                    binding.btnForgotPassword.visibility = View.VISIBLE
                    binding.btnClickSignUp.visibility = View.VISIBLE
                    binding.txtOptionsOR.visibility = View.VISIBLE
                    Toast.makeText(
                        requireActivity(),
                        "Invalid email/password",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handlerResults(task)
            }

        }

    private fun handlerResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(requireActivity(), task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(requireActivity(), HomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(requireActivity(), it.exception.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
