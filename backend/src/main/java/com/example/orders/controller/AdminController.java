package com.example.orders.controller;

import com.example.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final OrderService orderService;
    private final String adminApiKey;

    public AdminController(OrderService orderService, @Value("${ADMIN_API_KEY}") String adminApiKey) {
        this.orderService = orderService;
        this.adminApiKey = adminApiKey;
    }

    @DeleteMapping("/orders")
    public ResponseEntity<String> deleteAllOrders(@RequestHeader(value = "X-Api-Key", required = false) String apiKey) {
        if (apiKey != null && apiKey.equals(adminApiKey)) {
            orderService.deleteAll();
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("forbidden");
    }
}
