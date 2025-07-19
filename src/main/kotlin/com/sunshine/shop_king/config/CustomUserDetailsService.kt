package com.sunshine.shop_king.config

import com.sunshine.shop_king.service.UserService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userService: UserService
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userService.findByEmail(email) 
            ?: throw UsernameNotFoundException("User not found with email: $email")

        val authorities = listOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.email)
            .password(user.password)
            .authorities(authorities)
            .build()
    }
}