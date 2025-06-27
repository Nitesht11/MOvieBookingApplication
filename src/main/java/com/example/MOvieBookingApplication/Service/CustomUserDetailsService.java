package com.example.MOvieBookingApplication.Service;
import com.example.MOvieBookingApplication.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService{

 @Autowired
 private UserRepository userRepository;

 // this class is for 1st login of the user
    //

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("user Not found"));
    }
}
