package com.squareboat.model.repository.remote

import com.squareboat.model.pojo.*
import com.squareboat.network.retrofit.ApiService
import com.squareboat.network.retrofit.DataResult
import com.squareboat.network.retrofit.NetworkOnlineDataRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class AuthorizationRepository(private val apiService: ApiService) {

    suspend fun userRegisterApi(value: UserSignupModel): Flow<DataResult<UserSignupModelResponse>> {
        return object : NetworkOnlineDataRepo<UserSignupModelResponse, UserSignupModelResponse>() {
            override suspend fun fetchDataFromRemoteSource(): Response<UserSignupModelResponse> {
                return apiService.registerApi(value)
            }
        }.asFlow().flowOn(Dispatchers.IO)
    }

    suspend fun userLoginApi(value: UserSignupModel): Flow<DataResult<UserSignupModelResponse>> {
        return object : NetworkOnlineDataRepo<UserSignupModelResponse, UserSignupModelResponse>() {
            override suspend fun fetchDataFromRemoteSource(): Response<UserSignupModelResponse> {
                return apiService.loginApi(value)
            }
        }.asFlow().flowOn(Dispatchers.IO)
    }


    suspend fun changePasswordApi(value: ChangePasswordModel): Flow<DataResult<ChangePasswordResponse>> {
        return object : NetworkOnlineDataRepo<ChangePasswordResponse, ChangePasswordResponse>() {
            override suspend fun fetchDataFromRemoteSource(): Response<ChangePasswordResponse> {
                return apiService.changePasswordApi(value)
            }
        }.asFlow().flowOn(Dispatchers.IO)
    }


    suspend fun forgotPasswordApi(
        value: String
    ): Flow<DataResult<ForgotPasswordRequestModel>> {
        return object :
            NetworkOnlineDataRepo<ForgotPasswordRequestModel, ForgotPasswordRequestModel>() {
            override suspend fun fetchDataFromRemoteSource(): Response<ForgotPasswordRequestModel> {
                return apiService.forgotPasswordApi(value)
            }
        }.asFlow().flowOn(Dispatchers.IO)
    }


    suspend fun createJobApi(value: CreateJobModel): Flow<DataResult<CreateJobResponse>> {
        return object : NetworkOnlineDataRepo<CreateJobResponse, CreateJobResponse>() {
            override suspend fun fetchDataFromRemoteSource(): Response<CreateJobResponse> {
                return apiService.createJobApi(value)
            }
        }.asFlow().flowOn(Dispatchers.IO)
    }


    suspend fun jobsApi(): Flow<DataResult<RecruiterJobsResponse>> {
        return object : NetworkOnlineDataRepo<RecruiterJobsResponse, RecruiterJobsResponse>() {
            override suspend fun fetchDataFromRemoteSource(): Response<RecruiterJobsResponse> {
                return apiService.jobsApi()
            }
        }.asFlow().flowOn(Dispatchers.IO)
    }

    suspend fun deletejobsApi(value: DeleteJobModel): Flow<DataResult<Void>> {
        return object : NetworkOnlineDataRepo<Void, Void>() {
            override suspend fun fetchDataFromRemoteSource(): Response<Void> {
                return apiService.deletejobApi(value)!!
            }
        }.asFlow().flowOn(Dispatchers.IO)
    }


    /**
     * candidate section
     *
     * **/


    suspend fun availableJobsApi(): Flow<DataResult<JobsResponse>> {
        return object : NetworkOnlineDataRepo<JobsResponse, JobsResponse>() {
            override suspend fun fetchDataFromRemoteSource(): Response<JobsResponse> {
                return apiService.availableJobsApi()
            }
        }.asFlow().flowOn(Dispatchers.IO)
    }


    suspend fun applyJobApi(value: DeleteJobModel): Flow<DataResult<AppliedJobResponse>> {
        return object : NetworkOnlineDataRepo<AppliedJobResponse, AppliedJobResponse>() {
            override suspend fun fetchDataFromRemoteSource(): Response<AppliedJobResponse> {
                return apiService.applyJobApi(value)!!
            }
        }.asFlow().flowOn(Dispatchers.IO)
    }

    suspend fun appliedJobsApi(): Flow<DataResult<JobsResponse>> {
        return object : NetworkOnlineDataRepo<JobsResponse, JobsResponse>() {
            override suspend fun fetchDataFromRemoteSource(): Response<JobsResponse> {
                return apiService.appliedJobsApi()
            }
        }.asFlow().flowOn(Dispatchers.IO)
    }

}