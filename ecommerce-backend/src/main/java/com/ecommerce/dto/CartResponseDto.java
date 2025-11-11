package com.ecommerce.dto;

import java.util.List;

public class CartResponseDto {
    private Long cartId;
    private Double totalPrice;
    private List<CartItemDetailDto> items;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItemDetailDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDetailDto> items) {
        this.items = items;
    }
}
