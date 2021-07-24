package com.squareboat.model.pojo

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: ChangePasswordData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ChangePasswordData(

	@field:SerializedName("skills")
	val skills: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("userRole")
	val userRole: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
