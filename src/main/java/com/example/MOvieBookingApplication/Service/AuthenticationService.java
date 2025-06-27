package com.example.MOvieBookingApplication.Service;

import com.example.MOvieBookingApplication.DTO.LoginRequestDTO;
import com.example.MOvieBookingApplication.DTO.LoginResponseDTO;
import com.example.MOvieBookingApplication.DTO.RegisterRequestDTO;
import com.example.MOvieBookingApplication.Entity.User;
import com.example.MOvieBookingApplication.JWT.JWTService;
import com.example.MOvieBookingApplication.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;
@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;   // this pack is user  authen jwt token//

    public User registerNormalUser(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent())  {
            throw new RuntimeException("user already register");
        }
        Set<String> roles = new HashSet<String>();
        roles.add("Role_User");

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);

        return userRepository.save(user);

    }

    public User registerAdminUser(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()){
            throw new RuntimeException("user already register");
        }
        Set<String> roles = new HashSet<String>();
        roles.add("Role_User");
        roles.add("Role_Admin");
        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);
        return userRepository.save(user);

    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(()->new RuntimeException("User Not Found"));

 //       authenticationManager.authenticate(          // this check data got and wht we are same or not//
       //         new UsernamePasswordAuthenticationToken(
         //               loginRequestDTO.getUsername(),
          //              loginRequestDTO.getPassword())//

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUsername(),
                            loginRequestDTO.getPassword())
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        String token = jwtService.generateToken(user);
           return LoginResponseDTO.builder()
                   .jwtToken(token)
                   .username(user.getUsername())
                   .roles(user.getRoles())
                   .build();

    }
}
