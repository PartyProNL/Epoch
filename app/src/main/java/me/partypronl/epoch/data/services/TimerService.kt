package me.partypronl.epoch.data.services

import me.partypronl.epoch.data.DatabaseManager
import me.partypronl.epoch.data.models.TimerModel

class TimerService {
    val timerDao = DatabaseManager.instance.db.timerDao()

    suspend fun getTimers(): List<TimerModel> {
        return timerDao.getAll()
    }

    suspend fun createTimer(name: String, ends: Long) {
        val timerModel = TimerModel(0L, name, System.currentTimeMillis(), ends)
        timerDao.insert(timerModel)
    }
}