package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.UserResponseDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repo.OrderRepository;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.service.UserClient;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final OrderService orderService;


    @PostMapping()
    public ResponseEntity<OrderDto> createOrder(
            @Valid @RequestBody OrderDto orderDto) {
        OrderDto order = orderService.createOrder(orderDto);
        return ResponseEntity.ok(order);
    }

    // GET /orders — получаем все заказы
    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    // DELETE /orders/user/123
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteOrdersByUserId(@PathVariable Long userId) {
        orderService.deleteAllOrdersByUserId(userId);
        return ResponseEntity.noContent().build();
    }











//    @PostMapping()
//    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
//        ResponseEntity<UserResponseDto> response = userClient.getUser(order.getUserId());
//
//        if (!response.getStatusCode().is2xxSuccessful()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.status(201).body(orderRepository.save(order));
//
//    }
}
