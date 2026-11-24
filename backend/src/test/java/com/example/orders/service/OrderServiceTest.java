package com.example.orders.service;

import com.example.orders.entity.Order;
import com.example.orders.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void findAllReturnsOrdersFromRepository() {
        when(orderRepository.findAll()).thenReturn(List.of(new Order(), new Order()));

        assertEquals(2, orderService.findAll().size());
    }
}
