package com.sunshine.shop_king.controller

import com.sunshine.shop_king.dto.CreateProductRequest
import com.sunshine.shop_king.dto.ProductDto
import com.sunshine.shop_king.dto.UpdateProductRequest
import com.sunshine.shop_king.service.ProductService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping
    fun getAllProducts(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "id") sortBy: String,
        @RequestParam(defaultValue = "asc") sortDir: String
    ): ResponseEntity<Page<ProductDto>> {
        val sort = if (sortDir.equals("desc", ignoreCase = true)) {
            Sort.by(sortBy).descending()
        } else {
            Sort.by(sortBy).ascending()
        }
        val pageable = PageRequest.of(page, size, sort)
        val products = productService.getAllProducts(pageable)
        return ResponseEntity.ok(products)
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<ProductDto> {
        val product = productService.getProductById(id)
        return ResponseEntity.ok(product)
    }

    @GetMapping("/search")
    fun searchProducts(
        @RequestParam name: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<ProductDto>> {
        val pageable = PageRequest.of(page, size)
        val products = productService.searchProducts(name, pageable)
        return ResponseEntity.ok(products)
    }

    @GetMapping("/category/{categoryId}")
    fun getProductsByCategory(
        @PathVariable categoryId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<ProductDto>> {
        val pageable = PageRequest.of(page, size)
        val products = productService.getProductsByCategoryId(categoryId, pageable)
        return ResponseEntity.ok(products)
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun createProduct(@RequestBody request: CreateProductRequest): ResponseEntity<ProductDto> {
        val product = productService.createProduct(request)
        return ResponseEntity(product, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody request: UpdateProductRequest
    ): ResponseEntity<ProductDto> {
        val product = productService.updateProduct(id, request)
        return ResponseEntity.ok(product)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }
}