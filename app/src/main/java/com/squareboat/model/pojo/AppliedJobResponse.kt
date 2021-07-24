package com.squareboat.model.pojo

data class AppliedJobResponse(
	val code: Int? = null,
	val data: AppliedData? = null,
	val success: Boolean? = null
)

data class AppliedData(
	val createdAt: String? = null,
	val id: String? = null,
	val updatedAt: String? = null
)

