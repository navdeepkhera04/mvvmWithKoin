package com.squareboat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareboat.model.pojo.CreateJobModel
import com.squareboat.model.pojo.CreateJobResponse
import com.squareboat.model.repository.remote.AuthorizationRepository
import com.squareboat.network.retrofit.DataResult
import com.squareboat.network.retrofit.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddJobViewModel(private val dataRepository: AuthorizationRepository) : ViewModel() {
    var newJobData = MutableLiveData<CreateJobModel>().apply {
        value = CreateJobModel()
    }
    
    
    private var _createJobResponseLiveData =
        MutableLiveData<Event<DataResult<CreateJobResponse>>>()

    var createJobResponseLiveData: LiveData<Event<DataResult<CreateJobResponse>>> =
        _createJobResponseLiveData


    fun hitApiCreateJob(): LiveData<Event<DataResult<CreateJobResponse>>> {
        viewModelScope.launch {
            val response = dataRepository.createJobApi(newJobData.value!!)
            withContext(Dispatchers.Main) {
                response.collect { _createJobResponseLiveData.postValue(Event(it)) }
            }
        }



        return createJobResponseLiveData
    }
}