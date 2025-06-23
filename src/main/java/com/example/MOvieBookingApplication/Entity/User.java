package com.example.MOvieBookingApplication.Entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Set;

@Entity
@Data
@Table(name ="Users")
public class User implements UserDetails {

    private  Long userId;
    private  String username;
    private   String email;
    private    String  password;

    @ElementCollection(fetch = FetchType.EAGER)  // because we want role everytime user is used//
    private Set<String> roles;


    public Collections<? extends GrantedAuthority>getAuthorities{
        return roles.;
    }
}
