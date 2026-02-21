package com.app.inventory.repository;

import com.app.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory,Long> {
    boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, int quantity);
}
