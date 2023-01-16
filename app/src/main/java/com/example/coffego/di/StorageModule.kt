package com.example.coffego.di

import android.content.Context
import android.content.SharedPreferences
import com.example.coffego.data.storage.cart.CartStorage
import com.example.coffego.data.storage.user_storage.UserStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(
        @ApplicationContext context: Context
    ) : SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    fun provideUserStorage(sharedPreferences: SharedPreferences) : UserStorage {
        return UserStorage(
            sharedPreferences = sharedPreferences
        )
    }

    @Provides
    @Singleton
    fun provideCartStorage() : CartStorage {
        return CartStorage()
    }

    companion object {
        const val SHARED_PREFS_NAME = "CoffeeGoApplication"
    }

}