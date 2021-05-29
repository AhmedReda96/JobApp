package com.example.jobapp.service

import com.example.jobapp.db.JobEntity
import retrofit2.Response
import retrofit2.http.*


interface Service {

    @GET("positions.json?description=api")
    suspend fun getJobsRequest(): Response<List<JobEntity>?>?


}