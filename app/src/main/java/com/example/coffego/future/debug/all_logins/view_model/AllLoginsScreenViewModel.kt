package com.example.coffego.future.debug.all_logins.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffego.domain.error.RequestError
import com.example.coffego.domain.repository.AllUsersRepository
import com.example.coffego.future.common.EventBase
import com.example.coffego.future.debug.all_logins.model.AllUsersScreenEvent
import com.example.coffego.future.debug.all_logins.model.AllUsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AllLoginsScreenViewModel @Inject constructor(
    private val usersRepositoryImpl: AllUsersRepository
) : ViewModel(), EventBase<AllUsersScreenEvent> {

    private val _state =
        mutableStateOf<AllUsersState>(AllUsersState.Static)
    val state: State<AllUsersState> by ::_state

    override fun onEvent(event: AllUsersScreenEvent) {
        when(event) {
            is AllUsersScreenEvent.LoadUsers -> loadUsersList()
        }
    }

    private fun loadUsersList() {
        viewModelScope.launch(
            Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
                Log.e(
                    TAG + "CoroutineExceptionHandler::",
                    throwable.message.toString()
                )
            }
        ) {
            //_state.value = AllUsersState.Loading
            _state.value = AllUsersState.Loading
            runCatching {
                usersRepositoryImpl.getAllUsers()
            }.onSuccess {
                withContext(Dispatchers.Main){
                    _state.value = AllUsersState.Success(it)
                }
            }.onFailure(::onError)
        }
    }

    private fun onError(throwable: Throwable){
        when(throwable){
            RequestError.ConnectionError ->
                _state.value = AllUsersState.ConnectionError
            else -> Log.e(
                TAG,
                throwable.message.toString()
            )
        }
    }

    companion object {
        const val TAG = "AllLoginsScreenViewModel::"
    }
}