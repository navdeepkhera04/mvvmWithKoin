package com.squareboat.network.retrofit

import androidx.annotation.MainThread
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

abstract class NetworkOnlineDataRepo<RESULT, REQUEST> {
    fun asFlow() = flow {
        emit(DataResult.Loading())
        try {
            val apiResponse = fetchDataFromRemoteSource()
            val data = apiResponse.body()

            if (apiResponse.isSuccessful && data != null) {
                emit(DataResult.Success(data))
            } else {
                // emit(DataResult.Failure("Something went wrong!"))
                if (apiResponse.code() == 401) {
                    // logout the user
                    emit(DataResult.Failure(getErrorMsg(apiResponse.errorBody()!!)))
                } else {
                    emit(DataResult.Failure(getErrorMsg(apiResponse.errorBody()!!)))
                }
            }
        } catch (e: Exception) {
            emit(
                DataResult.Failure(
                    e.message
                )
            )

        }

    }

    //    private fun getErrorMessage(e: HttpException): String {
//        return getErrorMsg(e.response()?.errorBody()!!)
//    }
    fun getErrorMsg(responseBody: ResponseBody): String {

        return try {
            val jsonObject = JSONObject(responseBody.string())

            jsonObject.getString("message")

        } catch (e: Exception) {
            e.message!!
        }

    }

    @MainThread
    protected abstract suspend fun fetchDataFromRemoteSource(): Response<REQUEST>
}