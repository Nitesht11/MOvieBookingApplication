package com.example.MOvieBookingApplication.JWT;

import com.example.MOvieBookingApplication.Respository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

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
        final String authHeader= request.getHeader("Authorization");// to get header of token//
        final String username;
        final String JwtToken;
// check if auth header is present n start with bearer
        if(authHeader==null|| !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

//  if all okay then extrct jwt token from header

        String jwtToken = authHeader.substring(7);
        username= jwtService.extractUsername(jwtToken);

// check if we have userame and no entry exist  yet in contextholder
        if (username!= null && SecurityContextHolder.getContext().getAuthentication()==null){

            var userDetails =userRepository.findByUsername(username)
                    .orElseThrow(()-> new RuntimeException(" user not found"));

// validaet the token
            if(jwtService.isTokenValid(jwtToken,userDetails)){
//               create authentiction  with user roles  this part gpt
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                //....
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(userDetails, null,authorities) ;
//                      set  Authen details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    update the security context holder with authentication
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }

        }filterChain.doFilter(request,response);
    }
}
