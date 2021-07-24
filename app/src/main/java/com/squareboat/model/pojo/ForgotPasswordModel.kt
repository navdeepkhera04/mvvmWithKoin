package com.squareboat.model.pojo

data class ForgotPasswordRequestModel(
    val code: Int? = null,
    val data: ForgotUserData? = null,
    val success: Boolean? = null
)

data class ForgotUserData(
    val valid: Boolean? = null,
    val createdAt: String? = null,
    val id: String? = null,
    val email: String? = null,
    val token: String? = null,
    val updatedAt: String? = null
)
