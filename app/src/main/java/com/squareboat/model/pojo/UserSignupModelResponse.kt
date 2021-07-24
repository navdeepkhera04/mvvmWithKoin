package com.squareboat.model.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserSignupModelResponse(
	val code: Int? = null,
	val data: UserData? = null,
	val success: Boolean? = null
): Parcelable

