package com.example.doodlekong.data.remote.ws.models

import com.example.doodlekong.util.Constants.TYPE_GAME_STATE

data class GameState(
    val drawingPlayer: String,
    val word: String
): BaseModel(TYPE_GAME_STATE)
