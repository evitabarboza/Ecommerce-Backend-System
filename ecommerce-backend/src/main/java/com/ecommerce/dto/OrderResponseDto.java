package com.ecommerce.dto;

import java.util.List;

public class OrderResponseDto {
    private Long orderId;
    private String status;
    private Double totalPrice;
    private List<OrderItemDetailDto> items;

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public List<OrderItemDetailDto> getItems() { return items; }
    public void setItems(List<OrderItemDetailDto> items) { this.items = items; }
}
