package com.squareboat.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareboat.model.pojo.ChangePasswordModel
import com.squareboat.model.pojo.ChangePasswordResponse
import com.squareboat.model.repository.local.UserRepository
import com.squareboat.model.repository.remote.AuthorizationRepository
import com.squareboat.network.retrofit.DataResult
import com.squareboat.network.retrofit.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangePasswordViewModel(private val dataRepository: AuthorizationRepository) : ViewModel() {
    var changePasswordData = MutableLiveData<ChangePasswordModel>().apply {
        value = ChangePasswordModel()
    }
    private var _changePasswordLiveData =
        MutableLiveData<Event<DataResult<ChangePasswordResponse>>>()

    var changePasswordLiveData: LiveData<Event<DataResult<ChangePasswordResponse>>> =
        _changePasswordLiveData


    fun hitApiChangePassword(string: String?): LiveData<Event<DataResult<ChangePasswordResponse>>> {
        changePasswordData.value?.apply {
            token = string
        }
        viewModelScope.launch {
            val response = dataRepository.changePasswordApi(changePasswordData.value!!)
            withContext(Dispatchers.Main) {
                response.collect { _changePasswordLiveData.postValue(Event(it)) }
            }
        }


        return changePasswordLiveData
    }
}