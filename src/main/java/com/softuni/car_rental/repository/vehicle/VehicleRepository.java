package com.softuni.car_rental.repository.vehicle;


import com.softuni.car_rental.model.entity.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);
}
