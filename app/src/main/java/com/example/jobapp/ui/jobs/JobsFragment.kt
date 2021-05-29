package com.example.jobapp.ui.jobs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jobapp.R
import com.example.jobapp.db.FavJobEntity
import com.example.jobapp.helper.JobAdapter
import com.example.jobapp.db.JobDao
import com.example.jobapp.db.MainDataBase
import kotlinx.android.synthetic.main.fragment_jobs.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class JobsFragment : Fragment() {
    private lateinit var jobAdapter: JobAdapter
    private lateinit var viewModel: JobsVM
    private var jobDao: JobDao? = null
    private  var favJobList: ArrayList<FavJobEntity> =ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jobs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {


        viewModel = ViewModelProvider(this).get(JobsVM::class.java)
        activity?.let {
            viewModel.init(it)
            jobDao = MainDataBase.getInstance(it)?.jobDao()
        }



        jobsRV.apply {
            layoutManager =
                GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            hasFixedSize()

        }

        swipe.setOnRefreshListener {
            getJobs()

        }



        getJobs()

    }



    private fun getJobs() {
        swipe.isRefreshing = true


        GlobalScope.launch(Dispatchers.Main) {
            jobDao!!.getJobs().distinctUntilChanged()
                .collect {
                    if (it!!.isEmpty()) {
                        Log.d("TAG", "logTag getRoomJobs:isEmpty")
                        viewModel.getJobs()
                    } else {
                        Log.d("TAG", "logTag getRoomJobs: notEmpty")
                        jobAdapter = JobAdapter(it, requireActivity())
                        jobAdapter.notifyDataSetChanged()
                        jobsRV.adapter = jobAdapter
                        swipe.isRefreshing = false
                    }

                }
        }


    }


}