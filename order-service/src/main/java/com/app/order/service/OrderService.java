package com.app.order.service;

import com.app.order.client.InventoryClient;
import com.app.order.dto.OrderRequest;
import com.app.order.event.OrderPlaced;
import com.app.order.model.Order;
import com.app.order.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String,OrderPlaced> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest){
        if(orderRequest.quantity() <= 0){
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero."
            );
        }
        Boolean isInStock = checkInventory(
                orderRequest.skuCode(),
                orderRequest.quantity()
        );

        if(!isInStock){
            throw new IllegalArgumentException(
                    "Product is not in stock, please try again later."
            );
        }

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        order.setSkuCode(orderRequest.skuCode());

        orderRepository.save(order);

        //send message to kafka topic
        //orderPlaced number and email
        OrderPlaced orderPlaced = new OrderPlaced();
        orderPlaced.setOrderNumber(order.getOrderNumber());
        orderPlaced.setEmail(orderRequest.userDetails().email());
        orderPlaced.setFirstName(orderRequest.userDetails().firstName());
        orderPlaced.setLastName(orderRequest.userDetails().lastName());

        log.info("Order placed event sent to Kafka for order number: {}", order.getOrderNumber());
        kafkaTemplate.send("order-placed", orderPlaced);
    }

    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    public Boolean checkInventory(String skuCode, int quantity){
        return inventoryClient.isInStock(skuCode, quantity);
    }

    public Boolean fallBackMethod(String skuCode, int quantity, Throwable t){
        log.error("Inventory service down for {} reason {}", skuCode, t.getMessage());
        return false;
    }
}
