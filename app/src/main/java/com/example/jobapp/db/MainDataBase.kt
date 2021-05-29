package com.example.jobapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [JobEntity::class,FavJobEntity::class], version = 1, exportSchema = false)
abstract class MainDataBase : RoomDatabase() {
    abstract fun jobDao(): JobDao?

    companion object {
        private var instance: MainDataBase? = null
        @Synchronized
        fun getInstance(context: Context): MainDataBase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDataBase::class.java,
                    "database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}