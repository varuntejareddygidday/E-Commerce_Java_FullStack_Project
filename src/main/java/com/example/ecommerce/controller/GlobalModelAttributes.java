package com.example.ecommerce.controller;

import com.example.ecommerce.cart.Cart;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("cartCount")
    public int cartCount(@ModelAttribute("cart") Cart cart) {
        if (cart == null) return 0;
        return cart.getItems().stream().mapToInt(i -> i.getQuantity()).sum();
    }
}
