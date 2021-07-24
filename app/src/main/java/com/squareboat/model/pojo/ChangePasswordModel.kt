package com.squareboat.model.pojo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChangePasswordModel(

    @SerializedName("token")
    var token: String? = null,

    var password: String? = null,
    var confirmPassword: String? = null,

    ) : Parcelable

