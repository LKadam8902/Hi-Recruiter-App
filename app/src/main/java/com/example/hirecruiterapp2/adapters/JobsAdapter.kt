package com.example.hirecruiterapp2.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hirecruiterapp2.R
import com.example.hirecruiterapp2.database.Jobs
import com.example.hirecruiterapp2.databinding.CardCustomerSrBinding

class JobsAdapter : RecyclerView.Adapter<JobsAdapter.JobViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Jobs>() {
        override fun areItemsTheSame(oldItem: Jobs, newItem: Jobs): Boolean {
            return oldItem.JobId == newItem.JobId
        }

        override fun areContentsTheSame(oldItem: Jobs, newItem: Jobs): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = CardCustomerSrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("onCreate","reached")
        return JobViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val curJob = differ.currentList[position]
        Log.d("onBind","reached")
        holder.bind(curJob)
    }

    inner class JobViewHolder(private val binding: CardCustomerSrBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(jobs: Jobs) {
            Log.d("onView","reached")
            binding.apply {
                txtJname.text = jobs.role
                txtJobType.text = jobs.type
                // Log with a default message if the variable is null
                Log.d("TAG", "Job ID: ${jobs.JobId}")
                Glide.with(itemView)
                    .load(R.drawable.pdf_file_icon_svg)
                    .into(companyPic)
            }
        }
    }

    // Update the list of jobs
    fun submitList(list: List<Jobs>) {
        differ.submitList(list)
    }
}
