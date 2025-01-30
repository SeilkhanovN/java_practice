package org.example.service;

import org.example.model.Product;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductService {
    private List<Product> products = new ArrayList<>();

    @PostConstruct
    public void init() {
        // Adding 10 products to the list
        products.add(new Product(1, "Laptop", 999.99));
        products.add(new Product(2, "Smartphone", 499.99));
        products.add(new Product(3, "Tablet", 299.99));
        products.add(new Product(4, "Headphones", 199.99));
        products.add(new Product(5, "Keyboard", 49.99));
        products.add(new Product(6, "Mouse", 29.99));
        products.add(new Product(7, "Monitor", 199.99));
        products.add(new Product(8, "Printer", 149.99));
        products.add(new Product(9, "Router", 79.99));
        products.add(new Product(10, "External Hard Drive", 89.99));
    }

    public void printAll() {
        products.forEach(System.out::println);
    }

    public Product findByTitle(String title) {
        return products.stream()
                .filter(product -> product.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    public List<Product> getAllProducts() {
        return products;
    }
}
