package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.repository.CustomerRepository;
import org.example.repository.ProductRepository;
import org.example.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// ShopService.java
@Service
@Slf4j
public class ShopService {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    public ShopService(CustomerRepository customerRepository,
                       ProductRepository productRepository,
                       PurchaseRepository purchaseRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void buyProduct(String customerName, String productTitle) {
        Customer customer = customerRepository.findByName(customerName)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + customerName));

        Product product = productRepository.findByTitle(productTitle)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productTitle));

        Purchase purchase = new Purchase();
        purchase.setCustomer(customer);
        purchase.setProduct(product);
        purchase.setPurchasePrice(product.getPrice());
        purchase.setPurchaseDate(LocalDateTime.now());

        purchaseRepository.save(purchase);
        log.info("Purchase completed: {} bought {} for {}", customerName, productTitle, product.getPrice());
    }

    public List<Product> getProductsByCustomer(String customerName) {
        return purchaseRepository.findByCustomerName(customerName).stream()
                .map(Purchase::getProduct)
                .collect(Collectors.toList());
    }

    public List<Customer> getCustomersByProduct(String productTitle) {
        return purchaseRepository.findByProductTitle(productTitle).stream()
                .map(Purchase::getCustomer)
                .collect(Collectors.toList());
    }

    public void removeCustomer(String customerName) {
        Customer customer = customerRepository.findByName(customerName)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + customerName));
        customerRepository.delete(customer);
    }

    public void removeProduct(String productTitle) {
        Product product = productRepository.findByTitle(productTitle)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productTitle));
        productRepository.delete(product);
    }
}