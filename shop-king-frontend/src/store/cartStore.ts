import { create } from 'zustand'
import { persist } from 'zustand/middleware'
import type { CartItem, Product } from '../types/index'

interface CartState {
  items: CartItem[]
  totalItems: number
  totalPrice: number
  addItem: (product: Product, quantity?: number) => void
  removeItem: (productId: number) => void
  updateQuantity: (productId: number, quantity: number) => void
  clearCart: () => void
  getItemQuantity: (productId: number) => number
}

export const useCartStore = create<CartState>()(
  persist(
    (set, get) => ({
      items: [],
      totalItems: 0,
      totalPrice: 0,
      
      addItem: (product, quantity = 1) => {
        const { items } = get()
        const existingItem = items.find(item => item.product.id === product.id)
        
        if (existingItem) {
          const updatedItems = items.map(item =>
            item.product.id === product.id
              ? { ...item, quantity: item.quantity + quantity }
              : item
          )
          set(state => ({
            items: updatedItems,
            totalItems: state.totalItems + quantity,
            totalPrice: state.totalPrice + (product.price * quantity)
          }))
        } else {
          const newItem: CartItem = { product, quantity }
          set(state => ({
            items: [...state.items, newItem],
            totalItems: state.totalItems + quantity,
            totalPrice: state.totalPrice + (product.price * quantity)
          }))
        }
      },

      removeItem: (productId) => {
        const { items } = get()
        const item = items.find(item => item.product.id === productId)
        if (item) {
          set(state => ({
            items: state.items.filter(item => item.product.id !== productId),
            totalItems: state.totalItems - item.quantity,
            totalPrice: state.totalPrice - (item.product.price * item.quantity)
          }))
        }
      },

      updateQuantity: (productId, quantity) => {
        const { items } = get()
        const item = items.find(item => item.product.id === productId)
        
        if (item && quantity > 0) {
          const quantityDiff = quantity - item.quantity
          const updatedItems = items.map(item =>
            item.product.id === productId
              ? { ...item, quantity }
              : item
          )
          set(state => ({
            items: updatedItems,
            totalItems: state.totalItems + quantityDiff,
            totalPrice: state.totalPrice + (item.product.price * quantityDiff)
          }))
        } else if (quantity === 0) {
          get().removeItem(productId)
        }
      },

      clearCart: () => {
        set({ items: [], totalItems: 0, totalPrice: 0 })
      },

      getItemQuantity: (productId) => {
        const item = get().items.find(item => item.product.id === productId)
        return item ? item.quantity : 0
      },
    }),
    {
      name: 'cart-storage',
    }
  )
)