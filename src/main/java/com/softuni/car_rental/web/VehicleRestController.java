package com.softuni.car_rental.web;

import com.softuni.car_rental.model.entity.vehicle.Vehicle;
import com.softuni.car_rental.service.vehicle.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class VehicleRestController {

    private final VehicleService vehicleService;

    public VehicleRestController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllCars() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }
}