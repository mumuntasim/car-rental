package com.softuni.car_rental.model.dto.inspection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InspectionAddDTO {
    private String registrationNumber;
    private boolean passed;
}