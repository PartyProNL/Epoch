package me.partypronl.epoch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.partypronl.epoch.data.services.TimerService
import javax.inject.Inject

@HiltViewModel
class CreateTimerViewModel @Inject constructor(): ViewModel() {
    private val timerService = TimerService()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _ends = MutableStateFlow(null as Long?)
    val ends = _ends.asStateFlow()

    private val _creating = MutableStateFlow(false)
    val creating = _creating.asStateFlow()

    fun setName(name: String) {
        _name.value = name
    }

    fun setEnds(ends: Long?) {
        _ends.value = ends
    }

    fun create() {
        if(_ends.value == null) return

        viewModelScope.launch(Dispatchers.IO) {
            _creating.value = true
            timerService.createTimer(_name.value, _ends.value!!)
            _creating.value = false
        }
    }
}