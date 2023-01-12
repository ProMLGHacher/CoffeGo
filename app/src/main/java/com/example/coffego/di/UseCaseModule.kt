package com.example.coffego.di

import com.example.coffego.domain.usecase.MailValidationUseCase
import com.example.coffego.domain.usecase.PasswordValidationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideMainValidationUseCase() : MailValidationUseCase {
        return MailValidationUseCase()
    }

    @Provides
    @Singleton
    fun providePasswordValidationUseCase() : PasswordValidationUseCase {
        return PasswordValidationUseCase()
    }

}