package com.softuni.car_rental.service;

import com.softuni.car_rental.service.vehicle.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyReportScheduler {

    private final Logger logger = LoggerFactory.getLogger(DailyReportScheduler.class);
    private final VehicleService vehicleService;

    public DailyReportScheduler(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void generateDailyReport() {
        int totalCars = vehicleService.getAllVehicles().size();
        logger.info("ДНЕВЕН ОТЧЕТ: В момента разполагаме с {} автомобила в каталога.", totalCars);
    }
}