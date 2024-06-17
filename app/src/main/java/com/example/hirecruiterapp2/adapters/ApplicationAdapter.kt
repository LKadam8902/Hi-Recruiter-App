package com.example.hirecruiterapp2.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hirecruiterapp2.R
import com.example.hirecruiterapp2.database.ApplicationModel
import com.example.hirecruiterapp2.databinding.CardAgentCustomerSrBinding

class ApplicationAdapter : RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder>() {
    private val diffUtil = object : DiffUtil.ItemCallback<ApplicationModel>() {
        override fun areItemsTheSame(
            oldItem: ApplicationModel,
            newItem: ApplicationModel
        ): Boolean {
            return oldItem.applicationId == newItem.applicationId
        }

        override fun areContentsTheSame(
            oldItem: ApplicationModel,
            newItem: ApplicationModel
        ): Boolean {
            return newItem == oldItem
        }
    }

    private val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationViewHolder {
        val binding =
            CardAgentCustomerSrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApplicationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
        val curRequest = differ.currentList[position]
        holder.bind(curRequest)
    }

    inner class ApplicationViewHolder(private val binding: CardAgentCustomerSrBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(application: ApplicationModel) {
            binding.apply {

                txtRname.text = application.role ?: "N/A"
                txtApplicationType.text = application.type ?: "N/A"
                txtCname.text = application.company ?: "N/A"
                txtSkillsNeed.text = application.skillsNeeded
                txtDatePosted.text = application.skillsNeeded
                // Log with a default message if the variable is null
                Log.d("TAG", "Application ID: ${application.applicationId ?: "N/A"}")
                val firstImageUrl = application.companyPic
                if (firstImageUrl != null) {
                    Glide.with(itemView)
                        .load(firstImageUrl)
                        .into(companyPic)
                } else {
                    // Clear any previous image
                    Glide.with(itemView)
                        .load(R.drawable.ic_launcher_background)
                        .into(companyPic)
                }
            }
        }
    }

    // Update the list of application
    fun submitList(list: List<ApplicationModel>) {
        differ.submitList(list)
    }
}