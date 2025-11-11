package com.ecommerce.controller;

import com.ecommerce.dto.OrderResponseDto;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public OrderResponseDto checkout(@RequestParam Long userId) {
        return orderService.checkout(userId);
    }

    @GetMapping
    public List<OrderResponseDto> getOrders(@RequestParam Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    @GetMapping("/{id}")
    public OrderResponseDto getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}/status")
    public OrderResponseDto updateStatus(@PathVariable Long id, @RequestBody StatusRequest request) {
        return orderService.updateOrderStatus(id, request.getStatus());
    }

    public static class StatusRequest {
        private String status;
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}
