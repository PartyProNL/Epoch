package me.partypronl.epoch.data

import androidx.room.Database
import androidx.room.RoomDatabase
import me.partypronl.epoch.data.dao.TimerDao
import me.partypronl.epoch.data.models.TimerModel

@Database(entities = [
    TimerModel::class
], version = 1)
abstract class EpochDatabase: RoomDatabase() {
    abstract fun timerDao(): TimerDao
}