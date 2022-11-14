package com.faircorp.viewmodel


import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.faircorp.FaircorpApplication
import com.faircorp.api.ApiServices
import com.faircorp.dao.HeaterDao
import com.faircorp.dto.HeaterDto
import com.faircorp.model.Heater
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HeaterViewModel(private val heaterDao: HeaterDao) : ViewModel() {
    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val heaterDao = (extras[APPLICATION_KEY] as FaircorpApplication).database.heaterDao()
                return HeaterViewModel(heaterDao) as T
            }
        }
    }
    fun findByRoomId(roomId: Long): LiveData<List<HeaterDto>> = liveData {
        val elements: List<HeaterDto> = withContext(Dispatchers.IO) {
            try {
                val response = ApiServices.heatersApiService.findByRoomId(roomId).execute() // (1)
                Log.i("HeaterViewModel","Response: $response")

                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val heaters: List<HeaterDto> = response.body() ?: emptyList()
                heaters.apply {
                    heaterDao.clearAll() // (2)
                    forEach { // (3)
                        heaterDao.create(
                            Heater(
                                id = it.id.toInt(),
                                name = it.name,
                                power = it.power,
                                heaterStatus = it.heaterStatus,
                                roomId = it.roomId.toInt()
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                Log.i("HeaterViewModel","Error: $e")
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                heaterDao.findByRoomId(roomId).map { it.toDto() } // (4)
            }
        }
        emit(elements)
    }

    fun findById(heaterId: Int): LiveData<HeaterDto> = liveData {
        viewModelScope.launch(Dispatchers.IO) {
            emit(heaterDao.findById(heaterId).toDto())
        }
    }

    fun switchHeater(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            ApiServices.heatersApiService.switchHeater(id).execute()
        }

    }

    fun updateHeater(heater: HeaterDto)= liveData {
        val element: HeaterDto = withContext(Dispatchers.IO) {
            try {
                val response = ApiServices.heatersApiService.updateHeater(heater).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val heater: HeaterDto = response.body() ?: heater
                heater.apply {
                    heaterDao.update(
                        Heater(
                            id = heater.id.toInt(),
                            name = heater.name,
                            power = heater.power,
                            heaterStatus = heater.heaterStatus,
                            roomId = heater.roomId.toInt()
                        )
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
               heater
                }
            }
        emit(element)
        }


    val networkState: MutableLiveData<State> by lazy {
        MutableLiveData<State>().also { it.value = State.ONLINE }
    }


}
enum class State { ONLINE, OFFLINE }

