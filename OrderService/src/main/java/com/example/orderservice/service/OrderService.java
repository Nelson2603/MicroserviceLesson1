package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.UserResponseDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserClient userClient;

    public OrderDto createOrder(OrderDto orderDto) {
        //получаем заказ из дто (преобразуем в сущность)
        Order order = orderMapper.toEntity(orderDto);//

        //получаем юзера из другого сервиса
        UserResponseDto userById = userClient.getUserById(order.getUserId());
        order.setUserId(userById.getId());// сетим айди из юзера его поле в заказ
        order.setNameUser(userById.getUsername());
        return orderMapper.toDto(orderRepository.save(order));
    }


    @Transactional
    public void deleteAllOrdersByUserId(Long userId) {
        orderRepository.deleteAllOrdersByUserId(userId);
    }


    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }
}

