package com.example.MOvieBookingApplication.Controller;


import com.example.MOvieBookingApplication.DTO.LoginRequestDTO;
import com.example.MOvieBookingApplication.DTO.LoginResponseDTO;
import com.example.MOvieBookingApplication.DTO.RegisterRequestDTO;
import com.example.MOvieBookingApplication.Entity.User;
import com.example.MOvieBookingApplication.Service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationService authenticationService;

    @PostMapping("/registerNormalUser")
    public ResponseEntity<User> registerNormalUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(authenticationService.registerNormalUser(registerRequestDTO));
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return  ResponseEntity.ok(authenticationService.login(loginRequestDTO));
    }



}



