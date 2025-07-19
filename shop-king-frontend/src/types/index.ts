export interface User {
  id: number
  email: string
  name: string
  phoneNumber?: string
  address?: string
  role: 'CUSTOMER' | 'ADMIN'
}

export interface Product {
  id: number
  name: string
  description?: string
  price: number
  stockQuantity: number
  imageUrl?: string
  categoryName: string
  isActive: boolean
}

export interface Category {
  id: number
  name: string
  description?: string
}

export interface OrderItem {
  id: number
  productName: string
  quantity: number
  unitPrice: number
  totalPrice: number
}

export interface Order {
  id: number
  totalAmount: number
  status: 'PENDING' | 'CONFIRMED' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED'
  shippingAddress?: string
  orderItems: OrderItem[]
  createdAt: string
}

export interface CartItem {
  product: Product
  quantity: number
}

export interface LoginRequest {
  email: string
  password: string
}

export interface RegisterRequest {
  email: string
  password: string
  name: string
  phoneNumber?: string
  address?: string
}

export interface LoginResponse {
  token: string
  user: User
}

export interface CreateOrderRequest {
  orderItems: {
    productId: number
    quantity: number
  }[]
  shippingAddress?: string
}

export interface ApiResponse<T> {
  data: T
  message?: string
  success: boolean
}