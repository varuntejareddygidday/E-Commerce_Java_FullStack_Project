package com.example.ecommerce.controller;

import com.example.ecommerce.cart.Cart;
import com.example.ecommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import com.example.ecommerce.service.OrderService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@SessionAttributes("cart")
public class CartController {

    private final ProductService productService;
    private final OrderService orderService;

    public CartController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }

    @GetMapping("/cart")
    public String viewCart(@ModelAttribute("cart") Cart cart, Model model) {
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("total", cart.getTotal());
        return "cart";
    }

    @GetMapping("/order-success")
    public String orderSuccess(@RequestParam Integer orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "order-success";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Integer productId,
                            @RequestParam(defaultValue = "1") Integer quantity,
                            @ModelAttribute("cart") Cart cart) {

        var productOpt = productService.getProductById(productId);
        productOpt.ifPresent(product -> cart.add(product, quantity));

        return "redirect:/products";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Integer productId,
                                 @ModelAttribute("cart") Cart cart) {
        cart.remove(productId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart(@ModelAttribute("cart") Cart cart) {
        cart.clear();
        return "redirect:/cart";
    }
    @PostMapping("/cart/inc")
    public String increaseQty(@RequestParam Integer productId,
                              @ModelAttribute("cart") com.example.ecommerce.cart.Cart cart) {
        var productOpt = productService.getProductById(productId);
        productOpt.ifPresent(p -> cart.add(p, 1));
        return "redirect:/cart";
    }

    @PostMapping("/cart/dec")
    public String decreaseQty(@RequestParam Integer productId,
                              @ModelAttribute("cart") com.example.ecommerce.cart.Cart cart) {

        var items = cart.getItems();
        for (var item : items) {
            if (item.getProduct().getProductId().equals(productId)) {
                int newQty = item.getQuantity() - 1;
                if (newQty <= 0) {
                    cart.remove(productId);
                } else {
                    item.setQuantity(newQty);
                }
                break;
            }
        }
        return "redirect:/cart";
    }
    @PostMapping("/checkout")
    public String checkout(@ModelAttribute("cart") Cart cart, Model model) {
        if (cart.isEmpty()) {
            return "redirect:/cart";
        }

        var order = orderService.placeOrder(cart);
        cart.clear();

        return "redirect:/order-success?orderId=" + order.getOrderId();
    }
}
