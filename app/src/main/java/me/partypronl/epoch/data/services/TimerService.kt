package me.partypronl.epoch.data.services

import me.partypronl.epoch.data.DatabaseManager
import me.partypronl.epoch.data.models.TimerModel

class TimerService {
    val timerDao = DatabaseManager.instance.db.timerDao()

    suspend fun getTimers(): List<TimerModel> {
        return timerDao.getAll()
    }

    suspend fun setPinned(timerId: Long, pinned: Boolean) {
        val timer = timerDao.getById(timerId) ?: return
        timer.pinned = pinned
        timerDao.insert(timer)
    }

    suspend fun createTimer(name: String, ends: Long): TimerModel {
        val timerModel = TimerModel(0L, name, System.currentTimeMillis(), ends, false)
        timerDao.insert(timerModel)
        return timerModel
    }

    suspend fun deleteTimer(timerId: Long) {
        timerDao.deleteById(timerId)
    }

    suspend fun undoDeleteTimer(timer: TimerModel) {
        timerDao.insert(timer)
    }
}