package com.squareboat.model.pojo

data class JobsResponse(
	val metadata: Metadata? = null,
	val code: Int? = null,
	val data: List<DataItem?>? = null,
	val success: Boolean? = null
)

data class DataItem(
	val createdAt: String? = null,
	val description: String? = null,
	val location: String? = null,
	val id: String? = null,
	val title: String? = null,
	val updatedAt: String? = null
)

data class Metadata(
	val count: Int? = null,
	val limit: String? = null
)

