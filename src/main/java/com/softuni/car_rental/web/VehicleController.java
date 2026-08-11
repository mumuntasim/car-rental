package com.softuni.car_rental.web;

import com.softuni.car_rental.service.vehicle.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cars")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/all")
    public String showAllCars(Model model) {
        model.addAttribute("cars", vehicleService.getAllVehicles());
        return "cars";
    }
    @GetMapping("/add")
    public String addCar(Model model) {
        if (!model.containsAttribute("vehicleAddDTO")) {
            model.addAttribute("vehicleAddDTO", new com.softuni.car_rental.model.dto.vehicle.VehicleAddDTO());
        }
        return "add-car";
    }

    @org.springframework.web.bind.annotation.PostMapping("/add")
    public String doAddCar(@jakarta.validation.Valid com.softuni.car_rental.model.dto.vehicle.VehicleAddDTO vehicleAddDTO,
                           org.springframework.validation.BindingResult bindingResult,
                           org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("vehicleAddDTO", vehicleAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.vehicleAddDTO", bindingResult);
            return "redirect:/cars/add";
        }

        vehicleService.addVehicle(vehicleAddDTO);
        return "redirect:/cars/all";
    }
}