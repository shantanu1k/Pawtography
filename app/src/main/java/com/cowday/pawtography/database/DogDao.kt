package com.cowday.pawtography.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cowday.pawtography.models.local.DogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogRecord(dog: DogEntity)

    @Query("SELECT * FROM dog_table ORDER BY timestamp DESC")
    fun getRecentDogRecords(): Flow<List<DogEntity>>

    @Query("""
        DELETE FROM dog_table WHERE timestamp = (SELECT MIN(timestamp) FROM dog_table)
    """)
    suspend fun deleteRecentDogRecord()

    @Query("DELETE FROM dog_table")
    suspend fun deleteAllDogRecords()
}