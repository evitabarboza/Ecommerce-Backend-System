package com.ecommerce.service.impl;

import com.ecommerce.dto.OrderItemDetailDto;
import com.ecommerce.dto.OrderResponseDto;
import com.ecommerce.entity.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                            CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public OrderResponseDto checkout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) throw new RuntimeException("Cart is empty");

        Order order = new Order();
        order.setUser(user);
        order.setStatus("PENDING");
        order.setTotalPrice(cart.getTotalPrice());

        List<OrderItem> orderItems = cart.getItems().stream().map(ci -> {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(ci.getProduct());
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(ci.getProduct().getPrice());
            return oi;
        }).collect(Collectors.toList());

        order.setItems(orderItems);
        orderRepository.save(order);

        // Clear the cart
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        return mapToDto(order);
    }

    @Override
    public List<OrderResponseDto> getOrdersByUser(Long userId) {
        return orderRepository.findByUser_Id(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToDto(order);
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
        return mapToDto(order);
    }

    private OrderResponseDto mapToDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(order.getId());
        dto.setStatus(order.getStatus());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setItems(order.getItems().stream().map(oi -> {
            OrderItemDetailDto d = new OrderItemDetailDto();
            d.setProductId(oi.getProduct().getId());
            d.setProductName(oi.getProduct().getName());
            d.setQuantity(oi.getQuantity());
            d.setPrice(oi.getPrice());
            return d;
        }).collect(Collectors.toList()));
        return dto;
    }
}
