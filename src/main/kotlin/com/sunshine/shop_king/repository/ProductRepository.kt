package com.sunshine.shop_king.repository

import com.sunshine.shop_king.entity.Category
import com.sunshine.shop_king.entity.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByIsActiveTrue(pageable: Pageable): Page<Product>
    fun findByCategoryAndIsActiveTrue(category: Category, pageable: Pageable): Page<Product>
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.name LIKE %:name%")
    fun findByNameContainingAndIsActiveTrue(name: String, pageable: Pageable): Page<Product>
    
    fun findByStockQuantityLessThan(quantity: Int): List<Product>
    
}