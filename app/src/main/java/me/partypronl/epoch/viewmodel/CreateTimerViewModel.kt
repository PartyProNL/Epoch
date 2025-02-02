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

    private val _endsTime = MutableStateFlow(0L)
    val endsTime = _endsTime.asStateFlow()

    private val _canCreate = MutableStateFlow(false)
    val canCreate = _canCreate.asStateFlow()

    private val _creating = MutableStateFlow(false)
    val creating = _creating.asStateFlow()

    fun setName(name: String) {
        _name.value = name
        updateCanCreate()
    }

    fun setEnds(ends: Long?) {
        _ends.value = ends
        updateCanCreate()
    }

    fun setEndsTime(endsTime: Long) {
        _endsTime.value = endsTime
        updateCanCreate()
    }

    private fun updateCanCreate() {
        _canCreate.value = false

        if(_name.value == "") return
        if(_name.value.isBlank()) return
        if(_name.value.isEmpty()) return

        if(_ends.value == null) return

        val endTime = _ends.value!! + _endsTime.value
        if(endTime < System.currentTimeMillis()) return

        _canCreate.value = true
    }

    suspend fun create() {
        if(_ends.value == null) return

        _creating.value = true
        timerService.createTimer(_name.value, _ends.value!! + _endsTime.value)
        _creating.value = false
    }
}