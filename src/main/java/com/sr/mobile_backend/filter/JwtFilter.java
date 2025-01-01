package com.sr.mobile_backend.filter;

import com.sr.mobile_backend.service.JwtService;
import com.sr.mobile_backend.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {


    JwtService service;


    MyUserDetailsService userDetailsService;

    JwtFilter(JwtService service,MyUserDetailsService userDetailsService){
        this.service = service;
        this.userDetailsService = userDetailsService;

    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        /*String path = request.getRequestURI();
        if (path.startsWith("/auth")) {
            // Skip JWT validation for /auth endpoints
            filterChain.doFilter(request, response);
            return;
        }*/

        String header = request.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer ")){
            String token = header.substring(7);

            String username = service.getUsername(token);
            if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(service.validateToken(userDetails,token)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                userDetails,null,userDetails.getAuthorities()
                            );

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }

        filterChain.doFilter(request,response);


    }
}
