package com.example.MOvieBookingApplication.Entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
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
@Table(name ="Users")
public class User implements UserDetails {

    private  Long userId;
    private  String username;
    private   String email;
    private    String  password;

    @ElementCollection(fetch = FetchType.EAGER)  // because we want role everytime user is used//
    private Set<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {// get authority meth converts role var in authority //
        return roles.stream ()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toCollection());
    }
}
