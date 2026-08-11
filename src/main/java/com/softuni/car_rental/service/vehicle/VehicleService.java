package com.softuni.car_rental.service.vehicle;

import com.softuni.car_rental.client.InspectionClient;
import com.softuni.car_rental.model.entity.user.User;
import com.softuni.car_rental.model.entity.vehicle.Vehicle;
import com.softuni.car_rental.repository.user.UserRepository;
import com.softuni.car_rental.repository.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final InspectionClient inspectionClient;
    private final UserRepository userRepository;

    public VehicleService(VehicleRepository vehicleRepository,
                          InspectionClient inspectionClient,
                          UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.inspectionClient = inspectionClient;
        this.userRepository = userRepository;
    }

    public boolean canVehicleBeRented(String registrationNumber) {
        Optional<Vehicle> vehicleOpt = vehicleRepository.findByRegistrationNumber(registrationNumber);

        if (vehicleOpt.isEmpty()) {
            return false;
        }

        System.out.println("--- Изпращане на заявка към микросървиса за кола: " + registrationNumber + " ---");
        boolean isInspectionValid = inspectionClient.checkInspectionValidity(registrationNumber);

        return isInspectionValid;
    }

    public void addInspection(String registrationNumber, boolean passed) {
        com.softuni.car_rental.model.dto.inspection.InspectionAddDTO dto = new com.softuni.car_rental.model.dto.inspection.InspectionAddDTO(registrationNumber, passed);
        inspectionClient.addInspection(dto);
    }

    public List<Vehicle> getAllAvailableVehicles() {
        return vehicleRepository.findByIsRentedFalse();
    }

    public List<Vehicle> getRentedCarsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Потребителят не е намерен!"));
        return user.getRentedCars();
    }

    public void addVehicle(com.softuni.car_rental.model.dto.vehicle.VehicleAddDTO addDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setMake(addDTO.getMake());
        vehicle.setModel(addDTO.getModel());
        vehicle.setRegistrationNumber(addDTO.getRegistrationNumber());
        vehicle.setPricePerDay(addDTO.getPricePerDay());
        vehicle.setRented(false);

        vehicleRepository.save(vehicle);
    }

    @org.springframework.transaction.annotation.Transactional
    public boolean rentVehicle(java.util.UUID vehicleId, String username) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Грешка: Колата не е намерена!"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Грешка: Потребителят не е намерен!"));

        addInspection(vehicle.getRegistrationNumber(), true);

        boolean canRent = canVehicleBeRented(vehicle.getRegistrationNumber());

        if (canRent && !vehicle.isRented()) {
            vehicle.setRented(true);
            user.getRentedCars().add(vehicle);
            userRepository.save(user);
            vehicleRepository.save(vehicle);
            return true;
        } else {
            return false;
        }
    }

    @org.springframework.transaction.annotation.Transactional
    public void returnVehicle(java.util.UUID vehicleId, String username) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Колата не е намерена!"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Потребителят не е намерен!"));

        user.getRentedCars().remove(vehicle);
        vehicle.setRented(false);

        userRepository.save(user);
        vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

}