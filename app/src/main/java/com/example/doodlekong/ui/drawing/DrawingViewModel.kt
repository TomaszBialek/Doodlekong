package com.example.doodlekong.ui.drawing

import androidx.lifecycle.ViewModel
import com.example.doodlekong.R
import com.example.doodlekong.util.DispatcherProvider
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DrawingViewModel @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val gson: Gson
): ViewModel() {

    private val _selectedColorButtonId = MutableStateFlow(R.id.rbBlack)
    val selectedColorButtonId: StateFlow<Int> = _selectedColorButtonId

    fun checkRadioButton(id: Int) {
        _selectedColorButtonId.value = id
    }
}