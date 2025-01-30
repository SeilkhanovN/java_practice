package org.example;

import org.example.component.Cart;
import org.example.model.Product;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("org.example"); // Replace with your package name
        context.refresh();

        ProductService productService = context.getBean(ProductService.class);
        Cart cart = context.getBean(Cart.class);
        OrderService orderService = context.getBean(OrderService.class);

        // Print all products
        productService.printAll();

        // Find a product by title and add it to the cart
        Product product = productService.findByTitle("Laptop");
        if (product != null) {
            cart.add(product);
        }

        // Add another product to the cart
        product = productService.findByTitle("Headphones");
        if (product != null) {
            cart.add(product);
        }

        // Create an order
        orderService.createOrder(cart);

        context.close();
    }
}