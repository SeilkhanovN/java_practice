package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ShopController.java
@RestController
@RequestMapping("/api/shop")
@Slf4j
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/customer")
    public Customer addCustomer(@RequestParam Customer customerName){
        return shopService.saveCustomer(customerName);
    }

    @PostMapping("/product")
    public Product addProduct(@RequestBody Product productName){
        return shopService.saveProduct(productName);
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyProduct(@RequestParam String customerName, @RequestParam String productTitle) {
        try {
            shopService.buyProduct(customerName, productTitle);
            return ResponseEntity.ok("Purchase successful");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/products/{customerName}")
    public ResponseEntity<List<Product>> getProductsByCustomer(@PathVariable String customerName) {
        try {
            List<Product> products = shopService.getProductsByCustomer(customerName);
            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/customers/{productTitle}")
    public ResponseEntity<List<Customer>> getCustomersByProduct(@PathVariable String productTitle) {
        try {
            List<Customer> customers = shopService.getCustomersByProduct(productTitle);
            return ResponseEntity.ok(customers);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/customer/{customerName}")
    public ResponseEntity<String> removeCustomer(@PathVariable String customerName) {
        try {
            shopService.removeCustomer(customerName);
            return ResponseEntity.ok("Customer removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/product/{productTitle}")
    public ResponseEntity<String> removeProduct(@PathVariable String productTitle) {
        try {
            shopService.removeProduct(productTitle);
            return ResponseEntity.ok("Product removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

