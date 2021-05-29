package com.example.jobapp.ui.jobs

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobapp.db.FavJobEntity
import com.example.jobapp.db.JobDao
import com.example.jobapp.db.JobEntity
import com.example.jobapp.db.MainDataBase
import com.example.jobapp.service.Client
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class JobsVM : ViewModel() {
    private val TAG = JobsVM::class.java.simpleName
    private var jobDao: JobDao? = null

    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context
    var jobsSF: MutableStateFlow<List<JobEntity>> =
        MutableStateFlow(
            emptyList()
        )

    fun init(activity: Activity) {
        this.context = activity
        jobDao = MainDataBase.getInstance(context)?.jobDao()

    }

    fun getJobs() {
        viewModelScope.launch {
            try {
                val response =
                    Client.getClient.getJobsRequest()
                if (response?.isSuccessful!!) {
                    Log.d(
                        TAG,
                        "logTag getJobs: response isSuccessful : ${response.body()?.size.toString()}"
                    )
                    response.body()?.let { jobDao?.insertJobList(it) }

                } else {
                    Log.d(
                        TAG,
                        "logTag getJobs: response isNotSuccessful"
                    )
                }


            } catch (e: Exception) {


            }


        }


    }


    fun insertFavJobs(fav: FavJobEntity) {

        viewModelScope.launch {
       jobDao?.insertFavoriteJob(fav)
            Log.d("TAG", "logTag insertFavJobs")

        }

    }


    fun deleteFavJobs(fav: String) {

        viewModelScope.launch {
            jobDao?.deleteFavoriteJob(fav)
            Log.d("TAG", "logTag deleteFavJobs")
        }

    }
}