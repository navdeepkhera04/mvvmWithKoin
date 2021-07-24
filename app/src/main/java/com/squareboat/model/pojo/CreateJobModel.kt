package com.squareboat.model.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName

@Parcelize
data class CreateJobModel(

	@SerializedName("description")
	var description: String? = null,

	@SerializedName("title")
	var title: String? = null,

	@SerializedName("location")
	var location: String? = null,
) : Parcelable