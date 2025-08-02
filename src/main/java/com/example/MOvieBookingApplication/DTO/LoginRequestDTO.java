package com.example.MOvieBookingApplication.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestDTO {

    private String username;
    private String password;
}

