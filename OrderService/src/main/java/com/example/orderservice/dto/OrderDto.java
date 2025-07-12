package com.example.orderservice.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;



import java.time.LocalDate;
@Data
public class OrderDto {
    private Long id;
    private LocalDate date;

    @NotNull(message = "userId is required")
    private long userId;

    private String productName;


}
