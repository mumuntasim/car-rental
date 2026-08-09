package com.softuni.car_rental.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inspection-service", url = "http://localhost:8081/api/inspections")
public interface InspectionClient {

    @GetMapping("/check/{regNumber}")
    boolean checkInspectionValidity(@PathVariable("regNumber") String regNumber);

    @org.springframework.web.bind.annotation.PostMapping("/add")
    void addInspection(@org.springframework.web.bind.annotation.RequestBody com.softuni.car_rental.model.dto.inspection.InspectionAddDTO addDTO);
}