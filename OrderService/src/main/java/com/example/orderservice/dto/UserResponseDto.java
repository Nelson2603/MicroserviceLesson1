package com.example.orderservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserResponseDto {

    private int id;
    private String username;
}
