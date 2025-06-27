package com.example.MOvieBookingApplication.JWT;

import com.example.MOvieBookingApplication.Respository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
     UserRepository userRepository;

    @Autowired
    private JWTService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        if (path.startsWith("/api/auth")) {
            // Skip JWT filter for registration/login
            filterChain.doFilter(request, response);
            return;
        }

        final  String authHeader= request.getHeader("Authorization");// to get header of token//
       final String jwtToken;
       final String username;

       if(authHeader == null||  !authHeader.startsWith("Bearer")){
           filterChain.doFilter(request,response);
           return;
           }

           // extrctoing jwt token frm header with sub string//

           jwtToken= authHeader.substring(7);
           username= jwtService.extractUsername(jwtToken);

           // check if we hav a username an  no authentiction exist yet//
           if (username != null && SecurityContextHolder.getContext().getAuthentication()==null){

               var userdetails =userRepository.findByUsername(username)
                       .orElseThrow(()-> new RuntimeException(" user not found"));

               // validate the token

               if( jwtService.isTokenValid(jwtToken, userdetails)) {


                   // create the authen with roles it is admin or user//

                   List<SimpleGrantedAuthority> authorities = userdetails.getRoles().stream()
                           .map (SimpleGrantedAuthority:: new)
                           .collect(Collectors.toList());

                   UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userdetails, null ,authorities);


                   // set  Authen details
                   authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                   //update the security context with authentication
                   SecurityContextHolder.getContext().setAuthentication(authToken);
               }
           }

          filterChain.doFilter(request,response);
       }

      }

