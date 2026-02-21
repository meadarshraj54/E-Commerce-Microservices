package com.app.inventory.service;

import com.app.inventory.repository.InventoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepo inventoryRepo;

    public boolean isInStock(String skuCode, int quantity) {
        return inventoryRepo.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
    }
}
