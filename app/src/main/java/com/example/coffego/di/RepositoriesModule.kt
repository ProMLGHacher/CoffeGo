package com.example.coffego.di

import com.example.coffego.data.network.all_users.AllUsersNetwork
import com.example.coffego.data.network.auth.AuthNetwork
import com.example.coffego.data.repository.AllUsersRepositoryImpl
import com.example.coffego.data.repository.AuthRepositoryImpl
import com.example.coffego.data.storage.user_storage.UserStorage
import com.example.coffego.domain.repository.AllUsersRepository
import com.example.coffego.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Provides
    fun provideAllUsersRepository(allUsersNetwork: AllUsersNetwork) : AllUsersRepository {
        return AllUsersRepositoryImpl(
            allUsersNetwork = allUsersNetwork
        )
    }

    @Provides
    fun provideAuthRepository(userStorage: UserStorage, authNetwork: AuthNetwork) : AuthRepository {
        return AuthRepositoryImpl(
            userStorage = userStorage,
            authNetwork = authNetwork
        )
    }

}