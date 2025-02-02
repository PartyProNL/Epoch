package me.partypronl.epoch.viewmodel.home

import me.partypronl.epoch.data.models.TimerModel

data class HomeEvent(val type: HomeEventType, val affectedTimer: TimerModel)