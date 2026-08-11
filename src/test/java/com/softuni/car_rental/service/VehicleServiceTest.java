package com.softuni.car_rental.service;

import com.softuni.car_rental.client.InspectionClient;
import com.softuni.car_rental.model.dto.vehicle.VehicleAddDTO;
import com.softuni.car_rental.model.entity.vehicle.Vehicle;
import com.softuni.car_rental.repository.user.UserRepository;
import com.softuni.car_rental.repository.vehicle.VehicleRepository;
import com.softuni.car_rental.service.vehicle.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepository mockVehicleRepository;

    @Mock
    private InspectionClient mockInspectionClient;

    @Mock
    private UserRepository mockUserRepository;

    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        vehicleService = new VehicleService(mockVehicleRepository, mockInspectionClient, mockUserRepository);
    }

    @Test
    void testGetAllVehicles() {
        Vehicle car = new Vehicle();
        car.setMake("Toyota");
        car.setModel("Corolla");
        when(mockVehicleRepository.findAll()).thenReturn(List.of(car));

        List<Vehicle> result = vehicleService.getAllVehicles();

        assertEquals(1, result.size(), "Списъкът трябва да съдържа точно 1 кола");
        assertEquals("Toyota", result.get(0).getMake(), "Марката трябва да е Toyota");
    }

    @Test
    void testAddVehicle() {
        VehicleAddDTO dto = new VehicleAddDTO();
        dto.setMake("Honda");
        dto.setModel("Civic");
        dto.setRegistrationNumber("CB1234");
        dto.setPricePerDay(BigDecimal.valueOf(50));

        vehicleService.addVehicle(dto);

        ArgumentCaptor<Vehicle> captor = ArgumentCaptor.forClass(Vehicle.class);
        verify(mockVehicleRepository).save(captor.capture());

        Vehicle savedVehicle = captor.getValue();
        assertEquals("Honda", savedVehicle.getMake());
        assertEquals("Civic", savedVehicle.getModel());
        assertEquals("CB1234", savedVehicle.getRegistrationNumber());
    }
}