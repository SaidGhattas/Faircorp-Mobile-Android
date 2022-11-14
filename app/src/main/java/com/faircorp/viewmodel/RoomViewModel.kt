package com.faircorp.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.faircorp.FaircorpApplication
import com.faircorp.api.ApiServices
import com.faircorp.dao.RoomDao
import com.faircorp.dto.RoomDto
import com.faircorp.model.Room

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomViewModel (private val roomDao: RoomDao) : ViewModel() {
    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val roomDao = (extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FaircorpApplication).database.roomDao()
                return RoomViewModel(roomDao) as T
            }
        }
    }
    fun findByBuildingId(buildingId: Long): LiveData<List<RoomDto>> = liveData {
        val elements: List<RoomDto> = withContext(Dispatchers.IO) {
            try {
                val response = ApiServices.RoomsApiService.findByBuildingId(buildingId).execute() // (1)
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val rooms: List<RoomDto> = response.body() ?: emptyList()
                rooms.apply {
                    roomDao.clearAll() // (2)
                    forEach { // (3)
                        roomDao.update(
                            Room(it.id.toInt(), it.name, it.buildingId,it.current_temperature,it.target_temperature)
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                roomDao.findByBuildingId(buildingId).map { it.toDto() }
            }
        }
        emit(elements)
    }

    fun findById(roomId: Int): LiveData<RoomDto> = liveData {
        viewModelScope.launch(Dispatchers.IO) {
            emit(roomDao.findById(roomId).toDto())
        }
    }





    fun updateRoom(room: RoomDto)= liveData {
        val element: RoomDto = withContext(Dispatchers.IO) {
            try {
                val response = ApiServices.RoomsApiService.updateRoom(room).execute()
                withContext(Dispatchers.Main) {
                    networkState.value = State.ONLINE
                }
                val room: RoomDto = response.body() ?: room
                room.apply {
                    roomDao.update(
                        Room(
                            id = room.id.toInt(),
                            name = room.name,
                            buildingId = room.buildingId,
                            currentTemperature = room.current_temperature,
                            targetTemperature = room.target_temperature
                        )
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    networkState.value = State.OFFLINE
                }
                room
            }
        }
        emit(element)
    }


    val networkState: MutableLiveData<State> by lazy {
        MutableLiveData<State>().also { it.value = State.ONLINE }
    }


}

