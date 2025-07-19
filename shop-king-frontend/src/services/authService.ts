import { api } from './api'
import type { LoginRequest, RegisterRequest, LoginResponse, User } from '../types/index'

export const authService = {
  async login(credentials: LoginRequest): Promise<LoginResponse> {
    const response = await api.post<LoginResponse>('/auth/login', credentials)
    return response.data
  },

  async register(userData: RegisterRequest): Promise<User> {
    const response = await api.post<User>('/auth/register', userData)
    return response.data
  },

  async getCurrentUser(): Promise<User> {
    const response = await api.get<User>('/auth/me')
    return response.data
  },

  logout() {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  },
}