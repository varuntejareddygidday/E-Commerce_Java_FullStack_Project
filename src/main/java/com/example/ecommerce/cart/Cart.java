package com.example.ecommerce.cart;

import com.example.ecommerce.model.Product;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

    private final Map<Integer, CartItem> items = new LinkedHashMap<>();

    public void add(Product product, int quantity) {
        int productId = product.getProductId();
        CartItem existingItem = items.get(productId);

        if (existingItem == null) {
            items.put(productId, new CartItem(product, quantity));
        } else {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        }
    }

    public void remove(int productId) {
        items.remove(productId);
    }

    public void clear() {
        items.clear();
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public double getTotal() {
        return items.values()
                .stream()
                .mapToDouble(CartItem::getLineTotal)
                .sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
