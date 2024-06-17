package com.example.hirecruiterapp2.fragments.agent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.hirecruiterapp2.R
import com.example.hirecruiterapp2.databinding.FragmentAddApplicationBinding
import com.example.hirecruiterapp2.databinding.FragmentAddJobPreferenceBinding
import com.example.hirecruiterapp2.fragments.user.CustomerHomeFragment
import com.example.hirecruiterapp2.switchFragmentUsecase
import com.example.hirecruiterapp2.viewmodel.ApplicationViewModel
import com.example.hirecruiterapp2.viewmodel.ComplaintViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddApplicationFragment : Fragment() {

    private val items = arrayOf("Hardware", "Software")
    private lateinit var binding: FragmentAddApplicationBinding
    private lateinit var adapterItems: ArrayAdapter<String>
    private lateinit var applicationViewModel: ApplicationViewModel
    private var ur2: Uri? = null
    private lateinit var selectedType: String
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddApplicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterItems = ArrayAdapter(requireActivity(), R.layout.list_complaint_types, items)
        binding.autoType.setAdapter(adapterItems)
        mAuth = FirebaseAuth.getInstance()

        binding.autoType.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                selectedType = parent.getItemAtPosition(position).toString()
            }

        applicationViewModel = ViewModelProvider(requireActivity())[ApplicationViewModel::class.java]

        binding.btnImg2.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .start(10)
        }

        binding.btnRegister.setOnClickListener {
            registerComplaint()
        }
    }

    private fun registerComplaint() {
        val role = binding.editTitle.text.toString()
        val skillsNeed = binding.textDesc.text.toString()
        val type = selectedType
        val company=binding.editCompany.text.toString()

        if (role.isEmpty()) {
            showToast("Please enter a role")
            return
        }
        if (type.isEmpty()) {
            showToast("Please select a role type ")
            return
        }
        if (ur2 == null ) {
            showToast("No company image posted")
            return
        }
        if (skillsNeed.isEmpty()) {
            showToast("Please give some skills needed")
            return
        }

        val currentDate = Date()
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedDate = formatter.format(currentDate)

        applicationViewModel.addApplicationById(company, role,type,ur2.toString(),skillsNeed, formattedDate)
        navigateToRecruiterHome()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToRecruiterHome() {
        parentFragmentManager.popBackStack()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
                    ur2 = uri
                    binding.btnImg2.setImageURI(ur2)


        }
    }
}