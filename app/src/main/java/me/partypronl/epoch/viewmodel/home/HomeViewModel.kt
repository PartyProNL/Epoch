package me.partypronl.epoch.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    fun updateTimers() {
        viewModelScope.launch(Dispatchers.IO) {
            _timers.value = timerService.getTimers()
        }
    }

    fun setPinned(timer: TimerModel, pinned: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            timerService.setPinned(timer.id, pinned)
            _timers.value = timerService.getTimers()

            if(pinned) _snackbarNotifications.emit(HomeEvent(HomeEventType.PIN, timer))
            else _snackbarNotifications.emit(HomeEvent(HomeEventType.UNPIN, timer))
        }
    }

    private val _deletingTimer = MutableStateFlow(false)
    val deletingTimer = _deletingTimer.asStateFlow()

    suspend fun deleteTimer(timer: TimerModel) {
        _deletingTimer.value = true
        timerService.deleteTimer(timer.id)
        _deletingTimer.value = false
        _snackbarNotifications.emit(HomeEvent(HomeEventType.DELETE, timer))
    }

    fun undoDelete(timer: TimerModel) {
        viewModelScope.launch(Dispatchers.IO) {
            timerService.undoDeleteTimer(timer)
            _timers.value = timerService.getTimers()
            _snackbarNotifications.emit(HomeEvent(HomeEventType.DELETE_UNDONE, timer))
        }
    }

    private val _snackbarNotifications = MutableSharedFlow<HomeEvent>()
    val snackbarNotifications = _snackbarNotifications.asSharedFlow()
}