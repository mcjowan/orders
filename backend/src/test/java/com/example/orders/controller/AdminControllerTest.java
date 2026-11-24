package com.example.orders.controller;

import com.example.orders.service.OrderService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.HttpStatus.FORBIDDEN;

class AdminControllerTest {

    @Test
    void deniesDeleteWhenNoAdminKeyIsConfigured() {
        OrderService orderService = mock(OrderService.class);
        AdminController controller = new AdminController(orderService, "");

        assertEquals(FORBIDDEN, controller.deleteAllOrders(null).getStatusCode());
        verifyNoInteractions(orderService);
    }
}
