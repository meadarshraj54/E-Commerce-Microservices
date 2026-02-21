package com.app.product_service.dto;

import jdk.jshell.Snippet;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(String id, String name, String description,BigDecimal price, String skuCode) {

}
