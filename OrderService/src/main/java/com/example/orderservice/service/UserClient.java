package com.example.orderservice.service;

import com.example.orderservice.dto.UserResponseDto;
import com.example.orderservice.exception.UserNotFoundException;
import com.example.orderservice.exception.UserServiceUnavailableException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.awt.font.TextHitInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;

    @Value("${user_service.url}")
    private String url;

    @PostConstruct
    public void init() {
        log.info("user service url: " + url);
    }

    public ResponseEntity<UserResponseDto> getUser(long user_id) {
        try {
            ResponseEntity<UserResponseDto> forEntity = restTemplate
                    .getForEntity(url + "/{id}", UserResponseDto.class, user_id);
            log.info("response from userService" + forEntity.getBody().toString());
            return forEntity;
        } catch (Exception e) {
            log.info("INTERNAL_SERVER_ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    //метод под сервисный класс, что бы работать с объектом
    public UserResponseDto getUserById(long user_id) {
        try {
            ResponseEntity<UserResponseDto> forEntity = restTemplate
                    .getForEntity(url + "/{id}", UserResponseDto.class, user_id);
            if(forEntity.getStatusCode()==HttpStatus.NOT_FOUND){
                throw new UserNotFoundException("User not found with id : "+user_id);
            }
            if(!forEntity.getStatusCode().is2xxSuccessful()){
                throw new
                        UserServiceUnavailableException("User service unavailable "
                        + forEntity.getStatusCode());
            }
            log.info("response from userService: {}", forEntity.getBody());
            return forEntity.getBody();
        }catch (RestClientException e) {
            log.error("User service error", e);
            throw new UserServiceUnavailableException("User service error",e);
        }

    }
}
