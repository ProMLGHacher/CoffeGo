package com.example.coffego.domain.usecase

class PasswordValidationUseCase {
    fun execute(value: String) : Boolean {
        if (value.length < 8) return false
        if (value.firstOrNull { it.isDigit() } == null) return false
        if (value.filter { it.isLetter() }.firstOrNull { it.isUpperCase() } == null) return false
        if (value.filter { it.isLetter() }.firstOrNull { it.isLowerCase() } == null) return false
        if (value.firstOrNull { !it.isLetterOrDigit() } == null) return false

        return true
    }
}