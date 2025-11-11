package com.ecommerce.service;

import com.ecommerce.dto.CartResponseDto;

public interface CartService {

    CartResponseDto getCartByUser(Long userId);

    CartResponseDto addItemToCart(Long userId, Long productId, int quantity);

    CartResponseDto updateItemInCart(Long userId, Long productId, int quantity);

    CartResponseDto removeItemFromCart(Long userId, Long productId);
}
