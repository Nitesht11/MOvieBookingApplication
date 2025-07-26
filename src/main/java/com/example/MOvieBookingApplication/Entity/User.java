package com.example.MOvieBookingApplication.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name ="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue  (strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String username;
    private   String email;
    private    String  password;

    @ElementCollection(fetch = FetchType.EAGER)  // because we want role everytime user is used//
    private Set<String> roles;

    @OneToMany (fetch = FetchType.EAGER)
    @JoinColumn(name= "booking_id",nullable = false )
    private List<Booking> booking ;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {// get authority meth converts role var in authority //
        return roles.stream ()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
