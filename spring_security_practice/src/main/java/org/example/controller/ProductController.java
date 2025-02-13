package org.example.controller;

import org.example.model.Product;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private List<Product> products = new ArrayList<>();

    public ProductController() {
        // Initialize with some products
        products.add(new Product(1L, "Laptop", 999.99));
        products.add(new Product(2L, "Smartphone", 499.99));
    }

    @GetMapping("/")
    public String listProducts(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("products", products);
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        return "products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        products.removeIf(product -> product.getId().equals(id));
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        model.addAttribute("product", product);
        return "edit-product";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute Product updatedProduct) {
        Product product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        return "redirect:/";
    }

    @PostMapping("/filter")
    public String filterProducts(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Model model) {
        List<Product> filteredProducts = products.stream()
                .filter(product -> (filter == null || product.getName().toLowerCase().contains(filter.toLowerCase())))
                .filter(product -> (minPrice == null || product.getPrice() >= minPrice))
                .filter(product -> (maxPrice == null || product.getPrice() <= maxPrice))
                .collect(Collectors.toList());
        model.addAttribute("products", filteredProducts);
        return "products";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

