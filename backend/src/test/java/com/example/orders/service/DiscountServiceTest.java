package com.example.orders.service;

import com.example.orders.entity.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountServiceTest {

    private final DiscountService discountService = new DiscountService();

    @Test
    void regularCustomerWithFewItemsPaysFullPrice() {
        Customer customer = new Customer();
        customer.setVip(false);

        double result = discountService.apply(customer, 2, 100.0);

        assertEquals(100.0, result, 0.001);
    }
}
