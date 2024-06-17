package com.example.hirecruiterapp2.fragments.agent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hirecruiterapp2.adapters.ApplicationAdapter
import com.example.hirecruiterapp2.databinding.FragmentAgentSrBinding
import com.example.hirecruiterapp2.viewmodel.ApplicationViewModel

class AgentSrFragment : Fragment() {


    private lateinit var binding: FragmentAgentSrBinding
    private lateinit var applicationSrAdapter: ApplicationAdapter
    private lateinit var applicationViewModel: ApplicationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        applicationViewModel = ViewModelProvider(requireActivity())[ApplicationViewModel::class.java]
        binding = FragmentAgentSrBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
         observeComplaints()
    }

     private fun observeComplaints() {
         applicationViewModel.observeApplicationsById().observeForever { applications ->
             // Update RecyclerView's data when complaints change
             applicationSrAdapter.submitList(applications)
         }
     }

    private fun prepareRecyclerView() {
        applicationViewModel.getApplicationsById()
        applicationSrAdapter = ApplicationAdapter() // Initialize with an empty list

        binding.ryc.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = applicationSrAdapter
        }
    }


}