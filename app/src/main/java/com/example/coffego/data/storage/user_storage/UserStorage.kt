package com.example.coffego.data.storage.user_storage

import android.content.SharedPreferences
import javax.inject.Inject


class UserStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    private val editor = sharedPreferences.edit()

    fun saveUser(user: User) {
        editor.putString(LOGIN_TAG, user.login)
        editor.putString(MAIL_TAG, user.mail)
        editor.putString(NAME_ICON_TAG, user.nameIcon)
        editor.putString(TOKEN_TAG, user.token)
        editor.commit()
    }

    fun getUser() : User {
        return User(
            login = sharedPreferences.getString(LOGIN_TAG, null),
            mail = sharedPreferences.getString(MAIL_TAG, null),
            nameIcon = sharedPreferences.getString(NAME_ICON_TAG, null),
            token = sharedPreferences.getString(TOKEN_TAG, null)
        )
    }

    fun getToken() : String? {
        return sharedPreferences.getString(TOKEN_TAG, null)
    }

    companion object {
        const val LOGIN_TAG = "USER_LOGIN"
        const val MAIL_TAG = "USER_MAIL"
        const val NAME_ICON_TAG = "NAME_ICON"
        const val TOKEN_TAG = "TOKEN"
    }
}