package com.example.userservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "OrderService", url = "http://localhost:7777")
public interface OrderServiceClient {

    @DeleteMapping("orders/user/{userId}")
    void deleteUser(@PathVariable("userId") Long userId);
}
