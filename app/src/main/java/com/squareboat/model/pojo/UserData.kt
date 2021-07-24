package com.squareboat.model.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserData(
    val skills: String? = null,
    val createdAt: String? = null,
    val name: String? = null,
    val id: String? = null,
    val userRole: Int? = null,
    val email: String? = null,
    val updatedAt: String? = null,
    val token: String? = null
): Parcelable
