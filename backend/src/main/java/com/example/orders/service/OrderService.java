package com.example.orders.service;

import com.example.orders.dto.CreateOrderRequest;
import com.example.orders.dto.OrderItemRequest;
import com.example.orders.entity.Customer;
import com.example.orders.entity.Order;
import com.example.orders.entity.OrderItem;
import com.example.orders.entity.OrderStatus;
import com.example.orders.repository.CustomerRepository;
import com.example.orders.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DiscountService discountService;

    @PersistenceContext
    private EntityManager em;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).get();
    }

    public Order createOrder(CreateOrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId()).get();
        log.info("Creating order for customer {} <{}>, phone {}",
                customer.getName(), customer.getEmail(), customer.getPhone());

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.NEW);
        order.setCreatedAt(Instant.now());
        order = orderRepository.save(order);

        double total = 0;
        for (OrderItemRequest requested : request.getItems()) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductName(requested.getProductName());
            item.setQuantity(requested.getQuantity());
            item.setUnitPrice(requested.getUnitPrice());
            order.getItems().add(item);
            total += requested.getUnitPrice() * requested.getQuantity();
        }

        total = discountService.apply(customer, request.getItems().size(), total);
        order.setTotal(total);
        return orderRepository.save(order);
    }

    public void cancel(Long id) {
        try {
            Order order = orderRepository.findById(id).orElseThrow();
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        } catch (Exception e) {
            // ignore
        }
    }

    @SuppressWarnings("unchecked")
    public List<Order> searchByStatus(String status) {
        String sql = "SELECT * FROM orders WHERE status = '" + status + "'";
        return em.createNativeQuery(sql, Order.class).getResultList();
    }

    public void deleteAll() {
        orderRepository.deleteAll();
    }
}
