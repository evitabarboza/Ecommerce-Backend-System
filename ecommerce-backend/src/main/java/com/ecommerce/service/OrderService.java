package com.ecommerce.service;

import com.ecommerce.dto.OrderResponseDto;
import java.util.List;

public interface OrderService {
    OrderResponseDto checkout(Long userId);
    List<OrderResponseDto> getOrdersByUser(Long userId);
    OrderResponseDto getOrderById(Long orderId);
    OrderResponseDto updateOrderStatus(Long orderId, String status);
}
