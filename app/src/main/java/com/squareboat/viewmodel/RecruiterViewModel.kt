package com.squareboat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareboat.model.pojo.*
import com.squareboat.model.repository.remote.AuthorizationRepository
import com.squareboat.network.retrofit.DataResult
import com.squareboat.network.retrofit.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class RecruiterViewModel(private val dataRepository: AuthorizationRepository) : ViewModel() {


    private var _deleteJobResponseLiveData =
        MutableLiveData<Event<DataResult<Void>>>()

    var deleteJobResponseLiveData: LiveData<Event<DataResult<Void>>> =
        _deleteJobResponseLiveData



    fun hitApideleteJob(value: DeleteJobModel): LiveData<Event<DataResult<Void>>> {
        viewModelScope.launch {
            val response = dataRepository.deletejobsApi(value)
            withContext(Dispatchers.Main) {
                response.collect { _deleteJobResponseLiveData.postValue(Event(it)) }
            }
        }
        
        return deleteJobResponseLiveData
    }
 


    private var _jobsResponseLiveData =
        MutableLiveData<Event<DataResult<RecruiterJobsResponse>>>()

    var jobsResponseLiveData: LiveData<Event<DataResult<RecruiterJobsResponse>>> =
        _jobsResponseLiveData

    fun hitApiGetJobs(): LiveData<Event<DataResult<RecruiterJobsResponse>>> {

        viewModelScope.launch {
            val response = dataRepository.jobsApi()
            withContext(Dispatchers.Main) {
                response.collect { _jobsResponseLiveData.postValue(Event(it)) }
            }

        }
        return jobsResponseLiveData
    }
}