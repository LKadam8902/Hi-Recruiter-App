package com.example.hirecruiterapp2.fragments.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hirecruiterapp2.R
import com.example.hirecruiterapp2.adapters.ApplicationAdapter
import com.example.hirecruiterapp2.databinding.FragmentCustomerhomeBinding
import com.example.hirecruiterapp2.switchFragmentUsecase
import com.example.hirecruiterapp2.viewmodel.ApplicationViewModel


class CustomerHomeFragment : Fragment() {
    private lateinit var binding: FragmentCustomerhomeBinding
    private lateinit var ApplicationSrAdapter: ApplicationAdapter
    private lateinit var applicationViewModel: ApplicationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        applicationViewModel =
            ViewModelProvider(requireActivity())[ApplicationViewModel::class.java]
        binding = FragmentCustomerhomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeComplaints()
        binding.btnaddCSR.setOnClickListener {
            val addCSR = AddJobPreferenceFragment()
            val navigationUseCase =
                switchFragmentUsecase(requireActivity().supportFragmentManager, R.id.homeFragment)
            navigationUseCase.navigateToFragment(addCSR)
        }
    }

    private fun observeComplaints() {
        applicationViewModel.observeAllApplications().observeForever { application ->
            if (application != null) {
                Log.d("observe application", "Application received: $application")
                ApplicationSrAdapter.submitList(application)
            } else {
                Log.d("observe application", "No application received")
            }
            Log.d("observe application", "Application is observed")
        }
    }

    private fun prepareRecyclerView() {
        applicationViewModel.getAllApplications()
        ApplicationSrAdapter = ApplicationAdapter() // Initialize with an empty list

        binding.csrRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ApplicationSrAdapter
        }
    }

}