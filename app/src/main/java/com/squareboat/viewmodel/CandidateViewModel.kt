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

class CandidateViewModel(private val dataRepository: AuthorizationRepository) : ViewModel() {
    private var _appliedJobResponseResponseLiveData =
        MutableLiveData<Event<DataResult<AppliedJobResponse>>>()

    var appliedJobResponseResponseLiveData: LiveData<Event<DataResult<AppliedJobResponse>>> =
        _appliedJobResponseResponseLiveData



    fun hitApiApplyJob(value: DeleteJobModel): LiveData<Event<DataResult<AppliedJobResponse>>> {
        viewModelScope.launch {
            val response = dataRepository.applyJobApi(value)
            withContext(Dispatchers.Main) {
                response.collect { _appliedJobResponseResponseLiveData.postValue(Event(it)) }
            }
        }

        return appliedJobResponseResponseLiveData
    }



    private var _jobsResponseLiveData =
        MutableLiveData<Event<DataResult<JobsResponse>>>()

    var jobsResponseLiveData: LiveData<Event<DataResult<JobsResponse>>> =
        _jobsResponseLiveData

    fun hitApiGetJobs(): LiveData<Event<DataResult<JobsResponse>>> {

        viewModelScope.launch {
            val response = dataRepository.availableJobsApi()
            withContext(Dispatchers.Main) {
                response.collect { _jobsResponseLiveData.postValue(Event(it)) }
            }

        }
        return jobsResponseLiveData
    }
}