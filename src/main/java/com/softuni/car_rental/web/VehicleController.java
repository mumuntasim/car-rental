package com.softuni.car_rental.web;

import com.softuni.car_rental.model.entity.vehicle.Vehicle;
import com.softuni.car_rental.service.vehicle.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/cars")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/all")
    public String showAllCars(Model model) {
        model.addAttribute("cars", vehicleService.getAllAvailableVehicles());
        return "cars";
    }

    @GetMapping("/add")
    public String addCar(Model model) {
        if (!model.containsAttribute("vehicleAddDTO")) {
            model.addAttribute("vehicleAddDTO", new com.softuni.car_rental.model.dto.vehicle.VehicleAddDTO());
        }
        return "add-car";
    }

    @PostMapping("/add")
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

    @PostMapping("/rent/{id}")
    public String rentCar(@PathVariable("id") UUID id,
                          Principal principal,
                          org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {

        boolean success = vehicleService.rentVehicle(id, principal.getName());

        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Успешно наехте автомобила!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Този автомобил не е преминал преглед или вече е нает.");
        }

        return "redirect:/cars/all";
    }

    @GetMapping("/users/profile")
    public String showProfile(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("username", username);
        model.addAttribute("rentedCars", vehicleService.getRentedCarsByUsername(username));
        return "profile";
    }

    @PostMapping("/return/{id}")
    public String returnCar(@PathVariable("id") UUID id,
                            Principal principal,
                            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        vehicleService.returnVehicle(id, principal.getName());
        redirectAttributes.addFlashAttribute("successMessage", "Автомобилът беше върнат успешно в каталога!");
        return "redirect:/cars/users/profile";
    }

    @Controller
    public class ProfileController {

        private final VehicleService vehicleService;

        public ProfileController(VehicleService vehicleService) {
            this.vehicleService = vehicleService;
        }

        @GetMapping("/users/profile")
        public String showProfile(java.security.Principal principal, org.springframework.ui.Model model) {
            String username = principal.getName();
            model.addAttribute("username", username);
            model.addAttribute("rentedCars", vehicleService.getRentedCarsByUsername(username));
            return "profile";
        }
    }

}