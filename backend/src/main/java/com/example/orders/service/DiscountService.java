package com.example.orders.service;

import com.example.orders.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    public double apply(Customer customer, int itemCount, double total) {
        if (customer.isVip()) {
            return total * 0.85;
        }
        if (itemCount > 10) {
            return total * 0.90;
        }
        return total;
    }
}
