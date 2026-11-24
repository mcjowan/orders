package com.example.orders.controller;

import com.example.orders.entity.Customer;
import com.example.orders.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> list() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Customer get(@PathVariable Long id) {
        return customerRepository.findById(id).get();
    }

    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }
}
