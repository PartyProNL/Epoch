package me.partypronl.epoch.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer")
data class TimerModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val started: Long,
    val ends: Long
)