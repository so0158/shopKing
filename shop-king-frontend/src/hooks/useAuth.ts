import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query'
import { authService } from '../services/authService'
import { useAuthStore } from '../store/authStore'
import type { LoginRequest, RegisterRequest } from '../types/index'

export const useAuth = () => {
  const { login: setAuth, logout: clearAuth, user, isAuthenticated } = useAuthStore()
  const queryClient = useQueryClient()

  const loginMutation = useMutation({
    mutationFn: authService.login,
    onSuccess: (data) => {
      setAuth(data.user, data.token)
      queryClient.invalidateQueries({ queryKey: ['user'] })
    },
  })

  const registerMutation = useMutation({
    mutationFn: authService.register,
    onSuccess: () => {
      // Optionally auto-login after registration
    },
  })

  const logout = () => {
    authService.logout()
    clearAuth()
    queryClient.clear()
  }

  const userQuery = useQuery({
    queryKey: ['user'],
    queryFn: authService.getCurrentUser,
    enabled: false, // Disable this query since /api/auth/me doesn't exist in backend
    staleTime: 5 * 60 * 1000, // 5 minutes
  })

  return {
    user: user, // Use user from Zustand store directly since API endpoint doesn't exist
    isAuthenticated,
    isLoading: loginMutation.isPending || registerMutation.isPending,
    login: (credentials: LoginRequest) => loginMutation.mutate(credentials),
    register: (userData: RegisterRequest) => registerMutation.mutate(userData),
    logout,
    loginError: loginMutation.error,
    registerError: registerMutation.error,
  }
}