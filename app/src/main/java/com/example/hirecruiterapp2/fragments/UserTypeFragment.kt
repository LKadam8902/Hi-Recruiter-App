package com.example.hirecruiterapp2.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hirecruiterapp2.R
import com.example.hirecruiterapp2.databinding.FragmentUserTypeBinding
import com.example.hirecruiterapp2.fragments.agent.AgentSignInFragment
import com.example.hirecruiterapp2.fragments.user.SignInFragment
import com.example.hirecruiterapp2.switchFragmentUsecase

class UserTypeFragment : Fragment() {
    private lateinit var binding: FragmentUserTypeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserTypeBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.lytIntro.visibility = View.GONE
            binding.layoutUserType.visibility = View.VISIBLE
        }, 5000)

        binding.cardCustomer.setOnClickListener {
            val signIn = SignInFragment()
            val navigationUseCase =
                switchFragmentUsecase(requireActivity().supportFragmentManager, R.id.logFragment)
            navigationUseCase.navigateToFragment(signIn)
        }
        binding.cardRecuiter.setOnClickListener {
            val recruiterSignIn=AgentSignInFragment()
            val navigationUseCase =
                switchFragmentUsecase(requireActivity().supportFragmentManager, R.id.logFragment)
            navigationUseCase.navigateToFragment(recruiterSignIn)
        }
    }

}