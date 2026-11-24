package com.example.orders.controller;

import com.example.orders.dto.CreateOrderRequest;
import com.example.orders.dto.OrderSummary;
import com.example.orders.entity.Order;
import com.example.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderSummary> list() {
        List<OrderSummary> result = new ArrayList<>();
        for (Order order : orderService.findAll()) {
            result.add(toSummary(order));
        }
        return result;
    }

    @GetMapping("/{id}")
    public OrderSummary get(@PathVariable Long id) {
        return toSummary(orderService.findById(id));
    }

    @PostMapping
    public OrderSummary create(@RequestBody CreateOrderRequest request) {
        return toSummary(orderService.createOrder(request));
    }

    @PostMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id) {
        orderService.cancel(id);
    }

    @GetMapping("/search")
    public List<OrderSummary> search(@RequestParam String status) {
        List<OrderSummary> result = new ArrayList<>();
        for (Order order : orderService.searchByStatus(status)) {
            result.add(toSummary(order));
        }
        return result;
    }

    private OrderSummary toSummary(Order order) {
        return new OrderSummary(
                order.getId(),
                order.getCustomer().getName(),
                order.getItems().size(),
                order.getTotal(),
                order.getStatus().name());
    }
}
