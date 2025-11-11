package com.ecommerce.service.impl;

import com.ecommerce.dto.CartItemDetailDto;
import com.ecommerce.dto.CartResponseDto;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository,
                           CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartResponseDto getCartByUser(Long userId) {
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return mapToDto(cart);
    }

    @Override
    public CartResponseDto addItemToCart(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (quantity <= 0) throw new RuntimeException("Quantity must be greater than zero");

        CartItem existingItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.getItems().add(item);
            cartItemRepository.save(item);
        }

        cart.recalculateTotal();
        cartRepository.save(cart);
        return mapToDto(cart);
    }

    @Override
    public CartResponseDto updateItemInCart(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        item.setQuantity(quantity);
        cartItemRepository.save(item);

        cart.recalculateTotal();
        cartRepository.save(cart);

        return mapToDto(cart);
    }

    @Override
    public CartResponseDto removeItemFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not in cart"));

        cart.getItems().remove(item);
        cartItemRepository.delete(item);

        cart.recalculateTotal();
        cartRepository.save(cart);

        return mapToDto(cart);
    }

    private CartResponseDto mapToDto(Cart cart) {
        CartResponseDto dto = new CartResponseDto();
        dto.setCartId(cart.getId());
        dto.setTotalPrice(cart.getTotalPrice());
        dto.setItems(cart.getItems().stream().map(item -> {
            CartItemDetailDto detail = new CartItemDetailDto();
            detail.setProductId(item.getProduct().getId());
            detail.setProductName(item.getProduct().getName());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getProduct().getPrice());
            return detail;
        }).collect(Collectors.toList()));
        return dto;
    }
}
