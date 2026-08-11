package com.softuni.car_rental.service.vehicle;

import com.softuni.car_rental.client.InspectionClient;

import com.softuni.car_rental.model.entity.vehicle.Vehicle;
import com.softuni.car_rental.repository.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final InspectionClient inspectionClient;

    public VehicleService(VehicleRepository vehicleRepository, InspectionClient inspectionClient) {
        this.vehicleRepository = vehicleRepository;
        this.inspectionClient = inspectionClient;
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

    public java.util.List<com.softuni.car_rental.model.entity.vehicle.Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public void addVehicle(com.softuni.car_rental.model.dto.vehicle.VehicleAddDTO addDTO) {
        com.softuni.car_rental.model.entity.vehicle.Vehicle vehicle = new com.softuni.car_rental.model.entity.vehicle.Vehicle();
        vehicle.setMake(addDTO.getMake());
        vehicle.setModel(addDTO.getModel());
        vehicle.setRegistrationNumber(addDTO.getRegistrationNumber());
        vehicle.setPricePerDay(addDTO.getPricePerDay());

        vehicleRepository.save(vehicle);
    }
}
