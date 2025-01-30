package org.example.service;

import org.example.component.Cart;
import org.example.model.Product;
import org.springframework.stereotype.Component;

@Component
public class OrderService {
    public void createOrder(Cart cart) {
        System.out.println("Order details:");
        cart.getProducts().forEach(System.out::println);

        double totalCost = cart.getProducts().stream()
                .mapToDouble(Product::getCost)
                .sum();

        System.out.println("Total cost: " + totalCost);
    }

}
