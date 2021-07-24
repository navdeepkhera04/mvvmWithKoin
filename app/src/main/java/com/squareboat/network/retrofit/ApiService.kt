package com.squareboat.network.retrofit

import com.squareboat.model.pojo.*
import com.squareboat.util.Const
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @POST(Const.API_SIGN_UP)
    suspend fun registerApi(@Body value: UserSignupModel): Response<UserSignupModelResponse>

    @POST(Const.API_LOGIN)
    suspend fun loginApi(@Body value: UserSignupModel): Response<UserSignupModelResponse>

    @GET(Const.API_FORGOT_PASSWORD)
    suspend fun forgotPasswordApi(@Query("email") email: String): Response<ForgotPasswordRequestModel>

    @GET(Const.API_RECRUITER_JOBS)
    suspend fun jobsApi(): Response<RecruiterJobsResponse>

    @POST(Const.API_JOBS)
    suspend fun createJobApi(@Body value: CreateJobModel): Response<CreateJobResponse>

    @HTTP(method = "DELETE", path = Const.API_JOBS, hasBody = true)
    suspend fun deletejobApi(@Body value: DeleteJobModel): Response<Void>


    /**
     * candidate section
     */


    @GET(Const.API_AVAILABLE_JOBS)
    suspend fun availableJobsApi(): Response<JobsResponse>

    @POST(Const.API_APPLY_JOB)
    suspend fun applyJobApi(@Body value: DeleteJobModel): Response<AppliedJobResponse>

    @GET(Const.API_APPLIED_JOBS)
    suspend fun appliedJobsApi(): Response<JobsResponse>


    @POST(Const.API_CHANGE_PASSWORD)
    suspend fun changePasswordApi(@Body value: ChangePasswordModel): Response<ChangePasswordResponse>

}