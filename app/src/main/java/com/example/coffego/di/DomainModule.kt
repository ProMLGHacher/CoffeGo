package com.example.coffego.di

import com.example.coffego.data.network.all_users.AllUsersNetwork
import com.example.coffego.data.repository.AllUsersRepositoryImpl
import com.example.coffego.domain.repository.AllUsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideAllUsersRepository(allUsersNetwork: AllUsersNetwork) : AllUsersRepository {
        return AllUsersRepositoryImpl(
            allUsersNetwork = allUsersNetwork
        )
    }

}