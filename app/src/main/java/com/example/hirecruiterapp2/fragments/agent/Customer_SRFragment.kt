package com.example.hirecruiterapp2.fragments.agent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hirecruiterapp2.adapters.JobsAdapter
import com.example.hirecruiterapp2.databinding.FragmentCustomerSRBinding
import com.example.hirecruiterapp2.viewmodel.ApplicationViewModel
import com.example.hirecruiterapp2.viewmodel.ComplaintViewModel


class Customer_SRFragment : Fragment() {

    private lateinit var binding: FragmentCustomerSRBinding
    private lateinit var jobsSrAdapter: JobsAdapter
    private lateinit var jobsViewModel: ComplaintViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        jobsViewModel = ViewModelProvider(requireActivity())[ComplaintViewModel::class.java]
        binding = FragmentCustomerSRBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
         observeComplaints()
    }


    private fun observeComplaints() {
        jobsViewModel.observeAllJobs().observeForever { jobs ->
            // Update RecyclerView's data when complaints change
            jobsSrAdapter.submitList(jobs)
        }
    }

    private fun prepareRecyclerView() {
        jobsViewModel.getAllJobs()
         jobsSrAdapter = JobsAdapter() // Initialize with an empty list

         binding.ryc2.apply {
             layoutManager = LinearLayoutManager(requireContext())
             adapter =jobsSrAdapter
         }

    }


}