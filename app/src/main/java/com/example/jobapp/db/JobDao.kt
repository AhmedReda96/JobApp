package com.example.jobapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

@Dao
interface JobDao {
    @Insert
    suspend fun insertJobList(jobEntityList: List<JobEntity>)

    @Query("SELECT *  From jobTable")
    abstract fun getJobs(): Flow<List<JobEntity>?>

    @Insert
    suspend fun insertFavoriteJob(fav: FavJobEntity)

    @Query("SELECT *  From favJobTable")
    abstract fun getFavoriteJob(): Flow<List<FavJobEntity>?>

    @Query("delete From favJobTable WHERE id LIKE :id")
    suspend fun deleteFavoriteJob(id: String)


}