package com.squareboat.model.repository.local

import com.squareboat.app.SquareBoatDemo
import com.squareboat.model.pojo.UserData
import com.squareboat.model.pojo.UserSignupModel
import com.squareboat.network.retrofit.ApiService
import com.squareboat.util.Const.USER_DETAILS
import com.squareboat.util.Const.USER_TOKEN
import com.squareboat.util.Prefs

class UserRepository(private val apiService: ApiService) {

    fun getCurrentUser(): UserSignupModel? {
        return Prefs.with(SquareBoatDemo.appContext)!!.getObject(USER_DETAILS,
            UserSignupModel::class.java)
    }

    fun saveUser(user: UserData) {
        Prefs.with(SquareBoatDemo.appContext)!!.save(USER_DETAILS, user)
    }

    fun saveToken(token: String) {
        Prefs.with(SquareBoatDemo.appContext)!!.save(USER_TOKEN, token)
    }

    fun getToken() = Prefs.with(SquareBoatDemo.appContext)!!.getString(USER_TOKEN, "")

    fun clearData() {
        Prefs.with(SquareBoatDemo.appContext)!!.removeAll()
    }
}