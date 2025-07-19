package com.sunshine.shop_king.dto

import com.sunshine.shop_king.entity.UserRole

data class UserDto(
    val id: Long,
    val email: String,
    val name: String,
    val phoneNumber: String?,
    val address: String?,
    val role: UserRole
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String,
    val phoneNumber: String?,
    val address: String?
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val user: UserDto
)