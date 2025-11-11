package com.ecommerce.controller;

import com.ecommerce.dto.CartResponseDto;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // GET /api/cart
    @GetMapping
    public CartResponseDto getCart(@RequestParam Long userId) {
        return cartService.getCartByUser(userId);
    }

    // POST /api/cart/add/{productId}
    @PostMapping("/add/{productId}")
    public CartResponseDto addItemToCart(@RequestParam Long userId,
                                         @PathVariable Long productId,
                                         @RequestParam int quantity) {
        return cartService.addItemToCart(userId, productId, quantity);
    }

    // PUT /api/cart/update/{productId}
    @PutMapping("/update/{productId}")
    public CartResponseDto updateItemInCart(@RequestParam Long userId,
                                            @PathVariable Long productId,
                                            @RequestParam int quantity) {
        return cartService.updateItemInCart(userId, productId, quantity);
    }

    // DELETE /api/cart/remove/{productId}
    @DeleteMapping("/remove/{productId}")
    public CartResponseDto removeItemFromCart(@RequestParam Long userId,
                                              @PathVariable Long productId) {
        return cartService.removeItemFromCart(userId, productId);
    }
}
