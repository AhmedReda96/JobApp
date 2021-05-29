package com.example.jobapp.helper

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobapp.R
import com.example.jobapp.db.FavJobEntity
import com.example.jobapp.db.JobEntity
import com.example.jobapp.ui.jobs.JobsVM
import kotlinx.android.synthetic.main.job_item_model.view.*


class JobAdapter() : RecyclerView.Adapter<JobAdapter.ViewHolder>(), Parcelable {
    private var jobList: List<JobEntity> = ArrayList()
    private lateinit var context: Context
    private lateinit var jobsVM: JobsVM

    constructor(parcel: Parcel) : this() {

    }


    constructor(
        jobList: List<JobEntity>,
        context: Context,
    ) : this() {
        this.jobList = jobList
        this.context = context

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.job_item_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return jobList.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: JobEntity = jobList[position]
        Glide.with(context).load(model.companyLogo).into(holder.companyImg)
        holder.jobTitle.text = model.title
        holder.companyName.text = model.company



        holder.itemView.setOnClickListener {
            val bundle = Bundle()

            if (model.id != null) {
                bundle.putString("id", model.id)

            } else {
                bundle.putString("id", "")

            }
            if (model.companyLogo != null) {
                bundle.putString("companyImg", model.companyLogo)

            } else {
                bundle.putString("companyImg", "")

            }

            if (model.title != null) {
                bundle.putString("jobTitle", model.title)

            } else {
                bundle.putString("jobTitle", "")

            }
            if (model.description != null) {
                bundle.putString("jobDescription", model.description)

            } else {
                bundle.putString("jobDescription", "")

            }
            if (model.type != null) {
                bundle.putString("jobType", model.type)

            } else {
                bundle.putString("jobType", "")

            }
            if (model.url != null) {
                bundle.putString("jobUrl", model.url)

            } else {
                bundle.putString("jobUrl", "")

            }
            if (model.companyUrl != null) {
                bundle.putString("companyUrl", model.companyUrl)

            } else {
                bundle.putString("companyUrl", "")

            }

            it.findNavController()
                .navigate(R.id.action_jobsFragment_to_jobDescriptionFragment, bundle)

        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val companyImg: ImageView = itemView.companyImg
        val jobTitle: TextView = itemView.jobTitle
        val companyName: TextView = itemView.companyName


    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<JobAdapter> {
        override fun createFromParcel(parcel: Parcel): JobAdapter {
            return JobAdapter(parcel)
        }

        override fun newArray(size: Int): Array<JobAdapter?> {
            return arrayOfNulls(size)
        }
    }
}