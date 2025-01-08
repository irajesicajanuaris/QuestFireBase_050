package com.example.pertemuan13.ui.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan13.model.Mahasiswa
import com.example.pertemuan13.repository.RepositoryMhs
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel (
    private val repositoryMhs: RepositoryMhs
) : ViewModel(){
    var mhsUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMhs()
    }

    fun getMhs() {
        viewModelScope.launch {
            repositoryMhs.getAllMhs()
                .onStart {
                    mhsUIState = HomeUiState.Loading
                }
                .catch {
                    mhsUIState = HomeUiState.Error(e = it)
                }
                .collect{
                    mhsUIState = if (it.isEmpty()) {
                        HomeUiState.Error(Exception("Belum ada data mahasiswa"))
                    } else{
                        HomeUiState.Success(it)
                    }
                }
        }
    }
}

sealed class HomeUiState{
    object Loading : HomeUiState()

    data class Success(val data: List<Mahasiswa>) : HomeUiState()

    data class Error(val e: Throwable) : HomeUiState()
}