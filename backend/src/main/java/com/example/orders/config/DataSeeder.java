package com.example.orders.config;

import com.example.orders.entity.Customer;
import com.example.orders.entity.Order;
import com.example.orders.entity.OrderItem;
import com.example.orders.entity.OrderStatus;
import com.example.orders.repository.CustomerRepository;
import com.example.orders.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public DataSeeder(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) {
        if (customerRepository.count() > 0) {
            return;
        }

        String[][] people = {
                {"Ana Petrovic", "ana@example.com", "+381601110001", "pays late"},
                {"Marko Jovanovic", "marko@example.com", "+381601110002", "complained twice"},
                {"Ivana Nikolic", "ivana@example.com", "+381601110003", "preferred customer"},
                {"Petar Ilic", "petar@example.com", "+381601110004", ""},
                {"Jelena Kovac", "jelena@example.com", "+381601110005", "wholesale"}
        };

        for (int i = 0; i < people.length; i++) {
            Customer customer = new Customer();
            customer.setName(people[i][0]);
            customer.setEmail(people[i][1]);
            customer.setPhone(people[i][2]);
            customer.setInternalNotes(people[i][3]);
            customer.setVip(i == 2);
            customer = customerRepository.save(customer);

            for (int o = 0; o < 2; o++) {
                Order order = new Order();
                order.setCustomer(customer);
                order.setStatus(o == 0 ? OrderStatus.NEW : OrderStatus.SHIPPED);
                order.setCreatedAt(Instant.now());

                double total = 0;
                for (int k = 1; k <= 3; k++) {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setProductName("Product " + (i * 10 + o * 3 + k));
                    item.setQuantity(k);
                    item.setUnitPrice(9.99 * k);
                    order.getItems().add(item);
                    total += item.getQuantity() * item.getUnitPrice();
                }
                order.setTotal(total);
                orderRepository.save(order);
            }
        }
    }
}
