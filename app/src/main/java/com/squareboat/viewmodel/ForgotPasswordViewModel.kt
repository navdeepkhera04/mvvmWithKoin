package com.squareboat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareboat.model.pojo.ForgotPasswordRequestModel
import com.squareboat.model.pojo.UserSignupModel
import com.squareboat.model.repository.local.UserRepository
import com.squareboat.model.repository.remote.AuthorizationRepository
import com.squareboat.network.retrofit.DataResult
import com.squareboat.network.retrofit.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForgotPasswordViewModel(private val dataRepository: AuthorizationRepository, private val userRepository: UserRepository) : ViewModel() {
    var loginData = MutableLiveData<UserSignupModel>().apply {
        value = UserSignupModel()
    }
    private var _loginResponseLiveData =
        MutableLiveData<Event<DataResult<ForgotPasswordRequestModel>>>()

    var loginResponseLiveData: LiveData<Event<DataResult<ForgotPasswordRequestModel>>> =
        _loginResponseLiveData


    fun hitApiForgotPassword(): LiveData<Event<DataResult<ForgotPasswordRequestModel>>> {
        viewModelScope.launch {
            val response = dataRepository.forgotPasswordApi(loginData.value!!.email!!)
            withContext(Dispatchers.Main) {
                response.collect { _loginResponseLiveData.postValue(Event(it)) }
            }
        }


        return loginResponseLiveData
    }



    fun saveToken(token: String){
        userRepository.saveToken(token)
    }
}