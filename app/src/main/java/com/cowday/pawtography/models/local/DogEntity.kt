package com.cowday.pawtography.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dog_table")
data class DogEntity(
    @ColumnInfo(name = "timestamp")
    @PrimaryKey
    val timestamp: Long,
    @ColumnInfo(name = "image_url")
    val imageUrl: String
)

