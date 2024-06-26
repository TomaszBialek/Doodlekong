package com.example.doodlekong.repository

import com.example.doodlekong.data.remote.respones.BasicApiResponse
import com.example.doodlekong.data.remote.ws.Room
import com.example.doodlekong.util.Resource

interface SetupRepository {

    suspend fun createRoom(room: Room): Resource<Unit>

    suspend fun getRooms(searchQuery: String): Resource<List<Room>>

    suspend fun joinRoom(username: String, roomName: String): Resource<Unit>
}