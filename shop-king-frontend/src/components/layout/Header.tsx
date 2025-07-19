import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { ShoppingCartIcon, UserIcon, MagnifyingGlassIcon } from '@heroicons/react/24/outline'
import { Button } from '../ui/button'
import { Input } from '../ui/input'
import { useAuth } from '../../hooks/useAuth'
import { useCartStore } from '../../store/cartStore'

export const Header: React.FC = () => {
  const { user, isAuthenticated, logout } = useAuth()
  const totalItems = useCartStore(state => state.totalItems)
  const navigate = useNavigate()
  const [searchQuery, setSearchQuery] = React.useState('')

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault()
    if (searchQuery.trim()) {
      navigate(`/search?q=${encodeURIComponent(searchQuery.trim())}`)
    }
  }

  return (
    <header className="sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
      <div className="container flex h-16 items-center">
        <div className="mr-4 hidden md:flex">
          <Link to="/" className="mr-6 flex items-center space-x-2">
            <span className="hidden font-bold sm:inline-block text-xl">
              ShopKing
            </span>
          </Link>
          <nav className="flex items-center space-x-6 text-sm font-medium">
            <Link
              to="/products"
              className="transition-colors hover:text-foreground/80 text-foreground/60"
            >
              상품
            </Link>
            <Link
              to="/categories"
              className="transition-colors hover:text-foreground/80 text-foreground/60"
            >
              카테고리
            </Link>
          </nav>
        </div>

        <div className="flex flex-1 items-center justify-between space-x-2 md:justify-end">
          <div className="w-full flex-1 md:w-auto md:flex-none">
            <form onSubmit={handleSearch} className="relative">
              <MagnifyingGlassIcon className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
              <Input
                type="search"
                placeholder="상품 검색..."
                className="pl-8 sm:w-[300px] md:w-[200px] lg:w-[300px]"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />
            </form>
          </div>

          <nav className="flex items-center space-x-2">
            <Button variant="ghost" size="icon" asChild>
              <Link to="/cart" className="relative">
                <ShoppingCartIcon className="h-5 w-5" />
                {totalItems > 0 && (
                  <span className="absolute -top-1 -right-1 bg-primary text-primary-foreground rounded-full text-xs w-5 h-5 flex items-center justify-center">
                    {totalItems}
                  </span>
                )}
              </Link>
            </Button>

            {isAuthenticated ? (
              <div className="flex items-center space-x-2">
                <Button variant="ghost" size="icon" asChild>
                  <Link to="/profile">
                    <UserIcon className="h-5 w-5" />
                  </Link>
                </Button>
                <Button variant="ghost" onClick={logout}>
                  로그아웃
                </Button>
              </div>
            ) : (
              <div className="flex items-center space-x-2">
                <Button variant="ghost" asChild>
                  <Link to="/login">로그인</Link>
                </Button>
                <Button asChild>
                  <Link to="/register">회원가입</Link>
                </Button>
              </div>
            )}
          </nav>
        </div>
      </div>
    </header>
  )
}