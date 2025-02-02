package me.partypronl.epoch.data.services

import me.partypronl.epoch.data.DatabaseManager
import me.partypronl.epoch.data.models.TimerModel

class TimerService {
    val timerDao = DatabaseManager.instance.db.timerDao()

    suspend fun getTimers(): List<TimerModel> {
        val timers = timerDao.getAll().toMutableList()
        timers.add(TimerModel(1L, "Lily comes back", System.currentTimeMillis(), System.currentTimeMillis()+1000*60))
        timers.add(TimerModel(1L, "Internship starts", System.currentTimeMillis()-1000*60, System.currentTimeMillis()+2000*60))
        return timers

//        return timerDao.getAll()
    }

    suspend fun createTimer(name: String, ends: Long) {
        val timerModel = TimerModel(0L, name, System.currentTimeMillis(), ends)
        timerDao.insert(timerModel)
    }
}