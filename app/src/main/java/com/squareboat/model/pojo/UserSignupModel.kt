package com.squareboat.model.pojo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserSignupModel(

    @SerializedName("userRole")
    var userRole: Int? = 1,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("email")
    var email: String? = null,


    @SerializedName("reset_password_otp")
    var reset_password_otp: String? = null,


//	var isBusinessAccount: Boolean=false,
    var password: String? = null,
    var confirmPassword: String? = null,
    var skills: String? = null,
    var isRecruiterAccount: Boolean=false,

    ) : Parcelable