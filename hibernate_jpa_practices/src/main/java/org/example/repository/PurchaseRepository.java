package org.example.repository;

import org.example.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByCustomerName(String customerName);
    List<Purchase> findByProductTitle(String productTitle);
}