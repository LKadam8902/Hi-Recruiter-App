package com.example.hirecruiterapp2.fragments.agent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hirecruiterapp2.databinding.FragmentAgentHomeBinding


class AgentHomeFragment : Fragment() {

    private lateinit var binding: FragmentAgentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAgentHomeBinding.inflate(layoutInflater)
        return binding.root
    }


}