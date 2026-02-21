package com.app.api_gateway.routes;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;


import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class Routes {

    // Define routes for product service
    @Bean
    public RouterFunction<ServerResponse> productServiceRoute(){
        return route("product_service")
                .route(RequestPredicates.GET("/api/product"), HandlerFunctions.http("http://localhost:8080"))
                .route(RequestPredicates.POST("/api/product"), HandlerFunctions.http("http://localhost:8080"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("product_service_circuit_breaker", URI.create("forward:/fallBackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productServiceSwaggerRoute(){
        return route("product_service_swagger")
                .route(RequestPredicates.GET("/aggregate/product-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8080"))
                .filter(setPath("/v3/api-docs"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("product_service_swagger_circuit_breaker", URI.create("forward:/fallBackRoute")))
                .build();
    }

    // Define routes for order service
    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute(){
        return route("order_service")
                .route(RequestPredicates.POST("/api/order"), HandlerFunctions.http("http://localhost:8081"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("order_service_circuit_breaker", URI.create("forward:/fallBackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceSwaggerRoute(){
        return route("order_service_swagger")
                .route(RequestPredicates.GET("/aggregate/order-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8081"))
                .filter(setPath("/v3/api-docs"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("order_service_swagger_circuit_breaker", URI.create("forward:/fallBackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute(){
        return route("inventory_service")
                .route(RequestPredicates.GET("/api/inventory"), HandlerFunctions.http("http://localhost:8082"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("inventory_service_circuit_breaker", URI.create("forward:/fallBackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute(){
        return route("inventory_service_swagger")
                .route(RequestPredicates.GET("/aggregate/inventory-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8082"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("inventory_service_Swagger_circuit_breaker", URI.create("forward:/fallBackRoute")))
                .filter(setPath("/v3/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallBackRoute(){
        return route("fallback_route")
                .GET("/fallBackRoute",
                                request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                        .body("Service is currently unavailable. Please try again later."))
                .build();
    }
}
