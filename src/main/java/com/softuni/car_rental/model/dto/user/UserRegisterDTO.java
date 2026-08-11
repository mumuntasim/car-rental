package com.softuni.car_rental.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {

    @NotBlank(message = "Потребителското име е задължително!")
    @Size(min = 3, max = 20, message = "Потребителското име трябва да е между 3 и 20 символа!")
    private String username;

    @NotBlank(message = "Имейлът е задължителен!")
    @Email(message = "Въведете валиден имейл адрес!")
    private String email;

    @NotBlank(message = "Паролата е задължителна!")
    @Size(min = 5, message = "Паролата трябва да е поне 5 символа!")
    private String password;
}