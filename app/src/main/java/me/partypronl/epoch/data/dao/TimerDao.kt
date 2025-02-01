package me.partypronl.epoch.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.partypronl.epoch.data.models.TimerModel

@Dao
interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timerModel: TimerModel): Long

    @Query("SELECT * FROM timer")
    suspend fun getAll(): List<TimerModel>
}