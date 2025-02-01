package me.partypronl.epoch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.partypronl.epoch.data.models.TimerModel
import me.partypronl.epoch.data.services.TimerService
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    private val timerService = TimerService()

    private val _timers = MutableStateFlow<List<TimerModel>>(emptyList())
    val timers = _timers.asStateFlow()

    private val _loadingTimers = MutableStateFlow(false)
    val loadingTimers = _loadingTimers.asStateFlow()

    init {
        loadTimers()
    }

    private fun loadTimers() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingTimers.value = true
            _timers.value = timerService.getTimers()
            _loadingTimers.value = false
        }
    }
}