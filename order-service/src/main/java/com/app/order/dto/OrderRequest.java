package com.app.order.dto;

import java.math.BigDecimal;

public record OrderRequest(Long id, String orderNumber, Integer quantity, String skuCode, BigDecimal price, UserDetails userDetails) {

    public record UserDetails(String email, String firstName, String lastName) {}
}
