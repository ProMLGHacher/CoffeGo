package com.example.coffego.future.login_screen.view_model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffego.domain.error.RequestError
import com.example.coffego.domain.model.UserAuthorization
import com.example.coffego.domain.repository.AuthRepository
import com.example.coffego.domain.usecase.PasswordValidationUseCase
import com.example.coffego.future.common.EventBase
import com.example.coffego.future.debug.all_logins.view_model.AllLoginsScreenViewModel
import com.example.coffego.future.login_screen.model.AuthScreenEvent
import com.example.coffego.future.login_screen.model.AuthScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authRepository: AuthRepository
) : ViewModel(), EventBase<AuthScreenEvent> {

    private val _state = mutableStateOf<AuthScreenState>(AuthScreenState.Loading)
    val state: State<AuthScreenState> by ::_state

    init {
        viewModelScope.launch {
            delay(400)
            _state.value = AuthScreenState.Auth()
        }
    }

    override fun onEvent(event: AuthScreenEvent) {
        when (event) {
            is AuthScreenEvent.SwitchToLogin -> {
                _state.value = (_state.value as AuthScreenState.Auth).copy(isLogin = true)
            }
            is AuthScreenEvent.SwitchToRegistration -> {
                _state.value = (_state.value as AuthScreenState.Auth).copy(isLogin = false)
            }
            is AuthScreenEvent.OnLoginChange -> {
                _state.value = (_state.value as AuthScreenState.Auth).copy(login = event.login)
            }
            is AuthScreenEvent.OnPasswordChange -> {
                _state.value =
                    (_state.value as AuthScreenState.Auth).copy(password = event.password)
            }
            is AuthScreenEvent.Submit -> {
                submit()
            }
        }
    }

    fun toast(text: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            }
            return@launch
        }
    }

    private fun submit() {
        val data = (_state.value as AuthScreenState.Auth)
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            Log.e(
                AllLoginsScreenViewModel.TAG + "CoroutineExceptionHandler::",
                throwable.message.toString()
            )
        }) {
            runCatching {
                authRepository.auth(
                    UserAuthorization(
                        login = data.login, password = data.password
                    )
                )
            }.onSuccess {
                _state.value = AuthScreenState.Success
                return@launch
            }.onFailure(::onError)
        }
    }

    private fun onError(throwable: Throwable) {
        when (throwable) {
            RequestError.ConnectionError -> _state.value = AuthScreenState.ConnectionError
            RequestError.UnAuthorized -> {
                toast("Неверные данные")
            }
            else -> Log.e(
                AllLoginsScreenViewModel.TAG, throwable.message.toString()
            )
        }
    }
}
