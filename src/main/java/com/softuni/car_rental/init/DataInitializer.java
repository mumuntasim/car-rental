package com.softuni.car_rental.init;

import com.softuni.car_rental.model.entity.vehicle.Vehicle;
import com.softuni.car_rental.repository.vehicle.VehicleRepository;
import com.softuni.car_rental.service.vehicle.VehicleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;

    public DataInitializer(VehicleRepository vehicleRepository, VehicleService vehicleService) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleService = vehicleService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (vehicleRepository.count() == 0) {
            Vehicle car = new Vehicle();
            car.setMake("Toyota");
            car.setModel("Corolla");
            car.setRegistrationNumber("CB1234AB");
            car.setPricePerDay(new BigDecimal("50.00"));
            vehicleRepository.save(car);
        }

        System.out.println("--- 1. Първа проверка (трябва да е false) ---");
        boolean isV1 = vehicleService.canVehicleBeRented("CB1234AB");
        System.out.println("Резултат: " + isV1);

        System.out.println("--- 2. Изпращане на POST заявка за създаване на преглед ---");
        vehicleService.addInspection("CB1234AB", true); // Трябва да добавим този метод в VehicleService!

        System.out.println("--- 3. Втора проверка (вече ТРЯБВА да е true) ---");
        boolean isV2 = vehicleService.canVehicleBeRented("CB1234AB");
        System.out.println("Резултат: " + isV2);
    }
}