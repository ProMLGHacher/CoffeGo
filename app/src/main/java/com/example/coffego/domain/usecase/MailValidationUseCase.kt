package com.example.coffego.domain.usecase

import android.util.Patterns

class MailValidationUseCase {
    fun execute(value: String) : Boolean {
        return if (value.isEmpty()) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(value).matches();
        }
    }
}