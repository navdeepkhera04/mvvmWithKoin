package com.squareboat.model.pojo

data class RecruiterJobsResponse(
	val code: Int? = null,
	val data: RecruiterData? = null,
	val success: Boolean? = null
)

data class RecruiterDataItem(
	val createdAt: String? = null,
	val description: String? = null,
	val location: String? = null,
	val id: String? = null,
	val title: String? = null,
	val updatedAt: String? = null
)

data class RecruiterData(
	val metadata: RecruiterMetadata? = null,
	val data: List<RecruiterDataItem?>? = null
)

data class RecruiterMetadata(
	val count: Int? = null,
	val limit: Int? = null
)

