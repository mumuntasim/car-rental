package com.softuni.car_rental.model.dto.vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class VehicleAddDTO {

    @NotBlank(message = "Марката е задължителна!")
    private String make;

    @NotBlank(message = "Моделът е задължителен!")
    private String model;

    @NotBlank(message = "Регистрационният номер е задължителен!")
    private String registrationNumber;

    @NotNull(message = "Цената е задължителна!")
    @Positive(message = "Цената трябва да е положително число!")
    private BigDecimal pricePerDay;
}