import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { Button } from '../components/ui/button'
import { Card, CardContent, CardHeader, CardTitle } from '../components/ui/card'
import { Input } from '../components/ui/input'
import { useCartStore } from '../store/cartStore'
import { useAuth } from '../hooks/useAuth'
import { useCreateOrder } from '../hooks/useOrders'
import { formatPrice } from '../lib/utils'
import { TrashIcon, MinusIcon, PlusIcon } from '@heroicons/react/24/outline'

export const CartPage: React.FC = () => {
  const { items, totalItems, totalPrice, updateQuantity, removeItem, clearCart } = useCartStore()
  const { isAuthenticated } = useAuth()
  const navigate = useNavigate()
  const createOrderMutation = useCreateOrder()

  const handleQuantityChange = (productId: number, newQuantity: number) => {
    if (newQuantity < 1) {
      removeItem(productId)
    } else {
      updateQuantity(productId, newQuantity)
    }
  }

  const handleCheckout = async () => {
    if (!isAuthenticated) {
      navigate('/login')
      return
    }

    const orderData = {
      orderItems: items.map(item => ({
        productId: item.product.id,
        quantity: item.quantity,
      })),
      shippingAddress: '기본 배송지', // 실제로는 사용자 주소 또는 입력받은 주소
    }

    createOrderMutation.mutate(orderData, {
      onSuccess: (order) => {
        navigate(`/orders/${order.id}`)
      },
    })
  }

  if (items.length === 0) {
    return (
      <div className="container py-8">
        <div className="text-center py-12">
          <h1 className="text-3xl font-bold tracking-tight mb-4">장바구니가 비어있습니다</h1>
          <p className="text-muted-foreground mb-8">
            상품을 담아보세요!
          </p>
          <Button asChild size="lg">
            <Link to="/products">쇼핑 계속하기</Link>
          </Button>
        </div>
      </div>
    )
  }

  return (
    <div className="container py-8">
      <div className="flex flex-col lg:flex-row gap-8">
        {/* Cart Items */}
        <div className="flex-1">
          <div className="flex items-center justify-between mb-6">
            <h1 className="text-3xl font-bold tracking-tight">장바구니</h1>
            <Button variant="outline" onClick={clearCart}>
              전체 삭제
            </Button>
          </div>

          <div className="space-y-4">
            {items.map((item) => (
              <Card key={item.product.id}>
                <CardContent className="p-4">
                  <div className="flex items-center space-x-4">
                    <Link 
                      to={`/products/${item.product.id}`}
                      className="flex-shrink-0 w-20 h-20 bg-muted rounded-lg flex items-center justify-center"
                    >
                      {item.product.imageUrl ? (
                        <img
                          src={item.product.imageUrl}
                          alt={item.product.name}
                          className="w-full h-full object-cover rounded-lg"
                        />
                      ) : (
                        <span className="text-xs text-muted-foreground">이미지</span>
                      )}
                    </Link>

                    <div className="flex-1 min-w-0">
                      <Link 
                        to={`/products/${item.product.id}`}
                        className="font-medium hover:text-primary"
                      >
                        {item.product.name}
                      </Link>
                      <p className="text-sm text-muted-foreground">
                        {item.product.categoryName}
                      </p>
                      <p className="text-lg font-semibold text-primary">
                        {formatPrice(item.product.price)}
                      </p>
                    </div>

                    <div className="flex items-center space-x-2">
                      <Button
                        variant="outline"
                        size="icon"
                        onClick={() => handleQuantityChange(item.product.id, item.quantity - 1)}
                        className="h-8 w-8"
                      >
                        <MinusIcon className="h-4 w-4" />
                      </Button>
                      
                      <Input
                        type="number"
                        value={item.quantity}
                        onChange={(e) => {
                          const newQuantity = parseInt(e.target.value) || 0
                          handleQuantityChange(item.product.id, newQuantity)
                        }}
                        className="w-16 text-center"
                        min="1"
                        max={item.product.stockQuantity}
                      />
                      
                      <Button
                        variant="outline"
                        size="icon"
                        onClick={() => handleQuantityChange(item.product.id, item.quantity + 1)}
                        disabled={item.quantity >= item.product.stockQuantity}
                        className="h-8 w-8"
                      >
                        <PlusIcon className="h-4 w-4" />
                      </Button>
                    </div>

                    <div className="text-right">
                      <p className="font-semibold">
                        {formatPrice(item.product.price * item.quantity)}
                      </p>
                      <Button
                        variant="ghost"
                        size="sm"
                        onClick={() => removeItem(item.product.id)}
                        className="text-destructive hover:text-destructive"
                      >
                        <TrashIcon className="h-4 w-4" />
                      </Button>
                    </div>
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        </div>

        {/* Order Summary */}
        <div className="lg:w-80">
          <Card>
            <CardHeader>
              <CardTitle>주문 요약</CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="flex justify-between">
                <span>상품 수량</span>
                <span>{totalItems}개</span>
              </div>
              
              <div className="flex justify-between">
                <span>상품 금액</span>
                <span>{formatPrice(totalPrice)}</span>
              </div>
              
              <div className="flex justify-between">
                <span>배송비</span>
                <span>무료</span>
              </div>
              
              <hr />
              
              <div className="flex justify-between text-lg font-semibold">
                <span>총 결제 금액</span>
                <span className="text-primary">{formatPrice(totalPrice)}</span>
              </div>

              <Button
                className="w-full"
                size="lg"
                onClick={handleCheckout}
                disabled={createOrderMutation.isPending}
              >
                {createOrderMutation.isPending ? '주문 중...' : '주문하기'}
              </Button>

              {!isAuthenticated && (
                <p className="text-sm text-muted-foreground text-center">
                  주문하려면 로그인이 필요합니다
                </p>
              )}

              <Button variant="outline" className="w-full" asChild>
                <Link to="/products">쇼핑 계속하기</Link>
              </Button>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  )
}