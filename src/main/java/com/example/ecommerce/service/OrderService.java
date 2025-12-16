package com.example.ecommerce.service;

import com.example.ecommerce.cart.Cart;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order placeOrder(Cart cart) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");
        order.setTotal(BigDecimal.valueOf(cart.getTotal()));

        for (var cartItem : cart.getItems()) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(cartItem.getProduct());
            oi.setQuantity(cartItem.getQuantity());

            BigDecimal price = cartItem.getProduct().getPrice();
            oi.setPrice(price);

            BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            oi.setLineTotal(lineTotal);

            order.getItems().add(oi);
        }

        return orderRepository.save(order);
    }
}
