package com.example.orders.dto;

public record OrderSummary(Long id, String customerName, int itemCount, double total, String status) {
}
