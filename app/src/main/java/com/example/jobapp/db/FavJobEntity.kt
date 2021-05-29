package com.example.jobapp.db


import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favJobTable")
data class FavJobEntity(
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    val id: String,
)
