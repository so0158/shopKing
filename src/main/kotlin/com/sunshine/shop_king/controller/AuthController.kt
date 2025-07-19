package com.sunshine.shop_king.controller

import com.sunshine.shop_king.dto.LoginRequest
import com.sunshine.shop_king.dto.LoginResponse
import com.sunshine.shop_king.dto.RegisterRequest
import com.sunshine.shop_king.dto.UserDto
import com.sunshine.shop_king.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<UserDto> {
        val user = userService.registerUser(request)
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )
        
        val user = userService.findByEmail(request.email)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val userDto = UserDto(
            id = user.id,
            email = user.email,
            name = user.name,
            phoneNumber = user.phoneNumber,
            address = user.address,
            role = user.role
        )

        val response = LoginResponse(
            token = "temp-token", 
            user = userDto
        )

        return ResponseEntity.ok(response)
    }
}