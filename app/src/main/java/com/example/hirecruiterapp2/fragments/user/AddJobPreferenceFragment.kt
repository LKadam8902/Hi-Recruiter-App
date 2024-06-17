package com.example.hirecruiterapp2.fragments.user

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hirecruiterapp2.R
import com.example.hirecruiterapp2.databinding.FragmentAddJobPreferenceBinding
import com.example.hirecruiterapp2.switchFragmentUsecase
import com.example.hirecruiterapp2.viewmodel.ComplaintViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddJobPreferenceFragment : Fragment() {
    private val items = arrayOf("Hardware", "Software")
    private lateinit var binding: FragmentAddJobPreferenceBinding
    private lateinit var adapterItems: ArrayAdapter<String>
    private lateinit var complaintViewModel: ComplaintViewModel
    private var ur1: Uri? = null
    private lateinit var selectedType: String
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddJobPreferenceBinding.inflate(inflater, container, false)
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

        complaintViewModel = ViewModelProvider(requireActivity())[ComplaintViewModel::class.java]

        binding.btnImg1.setOnClickListener {
         openFileSelector()
        }

        binding.btnRegister.setOnClickListener {
            registerComplaint()
        }
    }

    private fun registerComplaint() {
        val title = binding.editTitle.text.toString()
        val description = binding.textDesc.text.toString()
        val type = selectedType

        if (title.isEmpty()) {
            showToast("Please enter a valid title")
            return
        }
        if (type.isEmpty()) {
            showToast("Please select the type of complaint")
            return
        }
        if (ur1 == null ) {
            showToast("No resume posted")
            return
        }
        if (description.isEmpty()) {
            showToast("Please give some description about the problem")
            return
        }

        val currentDate = Date()
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedDate = formatter.format(currentDate)

        complaintViewModel.addComplaintById(title, type, description,ur1.toString(), formattedDate)
        navigateToCustomerHome()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToCustomerHome() {
        val cHomeFragment = CustomerHomeFragment()
        val navigationUseCase = switchFragmentUsecase(
            requireActivity().supportFragmentManager,
            R.id.homeFragment
        )
        navigationUseCase.navigateToFragment(cHomeFragment)
    }

    fun openFileSelector() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
        }
        startActivityForResult(intent, PICK_FILE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            ur1 = data.data
            // Handle the file URI, e.g., upload it to the server
        }
    }

    companion object {
        private const val PICK_FILE_REQUEST = 1
    }
}