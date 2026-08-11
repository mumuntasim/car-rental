package com.softuni.car_rental.web;

import com.softuni.car_rental.model.dto.user.UserRegisterDTO;
import com.softuni.car_rental.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }


    @GetMapping("/register")
    public String register(org.springframework.ui.Model model) {
        if (!model.containsAttribute("userRegisterDTO")) {
            model.addAttribute("userRegisterDTO", new UserRegisterDTO());
        }
        return "register";
    }


    @PostMapping("/register")
    public String doRegister(@jakarta.validation.Valid UserRegisterDTO registerDTO,
                             org.springframework.validation.BindingResult bindingResult,
                             org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterDTO", registerDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);
            return "redirect:/users/register";
        }

        authService.registerUser(registerDTO);
        return "redirect:/users/login";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }
}