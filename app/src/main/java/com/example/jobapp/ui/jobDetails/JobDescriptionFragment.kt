package com.example.jobapp.ui.jobDetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.jobapp.R
import com.example.jobapp.db.FavJobEntity
import com.example.jobapp.db.JobDao
import com.example.jobapp.db.MainDataBase
import com.example.jobapp.ui.jobs.JobsVM
import kotlinx.android.synthetic.main.fragment_job_description.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class JobDescriptionFragment : Fragment(), View.OnClickListener {
    private lateinit var companyImgTxt: String
    private lateinit var jobTitleTxt: String
    private lateinit var jobDescriptionTxt: String
    private lateinit var jobTypeTxt: String
    private lateinit var jobUrlTxt: String
    private lateinit var companyUrlTxt: String
    private lateinit var idTxt: String
    private var jobDao: JobDao? = null
    private var favJobList: ArrayList<FavJobEntity> = ArrayList()
    private lateinit var viewModel: JobsVM
    private var favFlag: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_job_description, container, false)
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
        getData()
        getFavJob()
        favBtn.setOnClickListener(this)

    }

    private fun getFavJob() {

        GlobalScope.launch(Dispatchers.Main) {

            jobDao!!.getFavoriteJob()
                .collect {
                    favJobList.clear()
                    if (it!!.isEmpty()) {
                        Log.d("TAG", "logTag getRoomFavJobs:isEmpty")
                        Log.d("TAG", "logTag favFlag:false")

                        favFlag = false
                    } else {
                        favJobList.addAll(it)
                        Log.d("TAG", "logTag getRoomFavJobs:isNotEmpty= ${favJobList.size}")
                        checkFav()
                    }


                }
        }

    }

    private fun checkFav() {
        for (id in favJobList) {
            Log.d("TAG", "logTag favFlag:$id")
            if (idTxt == id.id) {
                Log.d("TAG", "logTag favFlag:true")
                favBtn.setBackgroundResource(R.drawable.ic_full_star)
                favFlag = true
            } else {
                Log.d("TAG", "logTag favFlag:false")
                favFlag = false
                favBtn.setBackgroundResource(R.drawable.ic_empty_star)

            }
        }

    }


    private fun getData() {
        companyImgTxt = arguments?.getString("companyImg").toString()
        jobTitleTxt = arguments?.getString("jobTitle").toString()
        jobDescriptionTxt = arguments?.getString("jobDescription").toString()
        jobTypeTxt = arguments?.getString("jobType").toString()
        jobUrlTxt = arguments?.getString("jobUrl").toString()
        companyUrlTxt = arguments?.getString("companyUrl").toString()
        idTxt = arguments?.getString("id").toString()
        Log.d("TAG", "logTag idText:$idTxt")

        activity?.let { Glide.with(it).load(companyImgTxt).into(companyImg) }

        jobTitle.text = jobTitleTxt
        jobDescription.text = jobDescriptionTxt
        jobType.text = jobTypeTxt
        jobUrl.text = jobUrlTxt
        companyUrl.text = companyUrlTxt
        jobTitle.text = jobTitleTxt

    }

    override fun onClick(v: View?) {

        if (favBtn.equals(v)) {
            if (favFlag) {
                viewModel.deleteFavJobs(idTxt)
                favBtn.setBackgroundResource(R.drawable.ic_empty_star)

            } else {
                viewModel.insertFavJobs(FavJobEntity(idTxt))
                favBtn.setBackgroundResource(R.drawable.ic_full_star)


            }

        }
    }


}