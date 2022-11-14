package com.faircorp.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.faircorp.FaircorpApplication
import com.faircorp.dao.WindowDao
import com.faircorp.api.ApiServices
import com.faircorp.model.Window
import com.faircorp.dto.WindowDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WindowViewModel(private val windowDao: WindowDao) : ViewModel() {
    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val windowDao = (extras[APPLICATION_KEY] as FaircorpApplication).database.windowDao()
                return WindowViewModel(windowDao) as T
            }
        }
    }
    fun findAll(): LiveData<List<WindowDto>> = liveData {
        val elements: List<WindowDto> = withContext(Dispatchers.IO) {
            try {
                val response = ApiServices.windowsApiService.findAll().execute() // (1)
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val windows: List<WindowDto> = response.body() ?: emptyList()
                windows.apply {
                    windowDao.clearAll() // (2)
                    forEach { // (3)
                        windowDao.create(
                            Window(
                                id = it.id.toInt(),
                                name = it.name,
                                roomId = it.roomId,
                                windowStatus = it.windowStatus
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                windowDao.findAll().map { it.toDto() } // (4)
            }
        }
        emit(elements)
    }

    fun findById(windowId: Int): LiveData<WindowDto> = liveData {
        viewModelScope.launch(Dispatchers.IO) {
            emit(windowDao.findById(windowId).toDto())
        }
    }
    fun switchWindow(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            ApiServices.windowsApiService.switchWindow(id).execute()
        }




    val networkState: MutableLiveData<State> by lazy {
        MutableLiveData<State>().also { it.value = State.ONLINE }
    }
}

    fun findByRoomId(roomId: Long): LiveData<List<WindowDto>> = liveData {
        val elements: List<WindowDto> = withContext(Dispatchers.IO) {
            try {
                val response = ApiServices.windowsApiService.findByRoomId(roomId).execute() // (1)
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val windows: List<WindowDto> = (response.body() ?: emptyList()) as List<WindowDto>
                windows.apply {
                    windowDao.clearAll() // (2)
                    forEach { // (3)
                        windowDao.create(
                            Window(
                                id = it.id.toInt(),
                                name = it.name,
                                windowStatus = it.windowStatus,
                                roomId = it.roomId
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                windowDao.findByRoomId(roomId.toInt()).map { it.toDto() } // (4)
            }
        }
        emit(elements)
    }

    fun updateWindow(window: WindowDto) = liveData {
        val element: WindowDto = withContext(Dispatchers.IO) {
            try {val response = ApiServices.windowsApiService.updateWindow(window).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val window: WindowDto = response.body() ?: window
                window.apply {
                    windowDao.create(
                        Window(
                            id = id.toInt(),
                            name = name,
                            roomId = roomId,
                            windowStatus = windowStatus
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                windowDao.findById(window.id.toInt()).toDto()
            }
        }
        emit(element)
    }


    val networkState: MutableLiveData<State> by lazy {
        MutableLiveData<State>().also { it.value = State.ONLINE }
    }}
//enum class State { ONLINE, OFFLINE }
