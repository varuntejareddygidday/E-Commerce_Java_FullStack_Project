package com.example.ecommerce.controller;

import com.example.ecommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @ModelAttribute("cart")
    public com.example.ecommerce.cart.Cart cart() {
        return new com.example.ecommerce.cart.Cart();
    }

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String listProducts(@RequestParam(required = false) String q,
                               Model model,
                               @ModelAttribute("cart") com.example.ecommerce.cart.Cart cart) {

        model.addAttribute("products", productService.searchProducts(q));
        model.addAttribute("q", q);

        int cartCount = cart.getItems().stream().mapToInt(i -> i.getQuantity()).sum();
        model.addAttribute("cartCount", cartCount);

        return "products";
    }

    @GetMapping("/products/{id}")
    public String productDetails(@PathVariable Integer id, Model model) {
        var productOpt = productService.getProductById(id);
        if (productOpt.isEmpty()) {
            return "redirect:/products";
        }
        model.addAttribute("product", productOpt.get());
        return "product-details";
    }

}
