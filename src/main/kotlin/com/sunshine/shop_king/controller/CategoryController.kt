package com.sunshine.shop_king.controller

import com.sunshine.shop_king.entity.Category
import com.sunshine.shop_king.repository.CategoryRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val categoryRepository: CategoryRepository
) {

    @GetMapping
    fun getAllCategories(): ResponseEntity<List<Category>> {
        val categories = categoryRepository.findAll()
        return ResponseEntity.ok(categories)
    }

    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: Long): ResponseEntity<Category> {
        val category = categoryRepository.findById(id).orElseThrow {
            NoSuchElementException("Category not found with id: $id")
        }
        return ResponseEntity.ok(category)
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun createCategory(@RequestBody category: Category): ResponseEntity<Category> {
        val savedCategory = categoryRepository.save(category)
        return ResponseEntity(savedCategory, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateCategory(
        @PathVariable id: Long,
        @RequestBody categoryRequest: Category
    ): ResponseEntity<Category> {
        val existingCategory = categoryRepository.findById(id).orElseThrow {
            NoSuchElementException("Category not found with id: $id")
        }

        val updatedCategory = Category(
            id = existingCategory.id,
            name = categoryRequest.name,
            description = categoryRequest.description
        )

        val savedCategory = categoryRepository.save(updatedCategory)
        return ResponseEntity.ok(savedCategory)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<Void> {
        categoryRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}