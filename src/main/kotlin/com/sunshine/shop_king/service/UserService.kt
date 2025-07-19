package com.sunshine.shop_king.service

import com.sunshine.shop_king.dto.RegisterRequest
import com.sunshine.shop_king.dto.UserDto
import com.sunshine.shop_king.entity.User
import com.sunshine.shop_king.entity.UserRole
import com.sunshine.shop_king.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun registerUser(request: RegisterRequest): UserDto {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        val user = User(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            name = request.name,
            phoneNumber = request.phoneNumber,
            address = request.address,
            role = UserRole.CUSTOMER
        )

        val savedUser = userRepository.save(user)
        return savedUser.toDto()
    }

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email).orElse(null)
    }

    fun findById(id: Long): User {
        return userRepository.findById(id).orElseThrow { 
            NoSuchElementException("User not found with id: $id") 
        }
    }

    private fun User.toDto(): UserDto {
        return UserDto(
            id = this.id,
            email = this.email,
            name = this.name,
            phoneNumber = this.phoneNumber,
            address = this.address,
            role = this.role
        )
    }
}