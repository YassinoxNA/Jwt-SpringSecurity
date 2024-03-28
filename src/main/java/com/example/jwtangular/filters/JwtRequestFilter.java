package com.example.jwtangular.filters;

import com.example.jwtangular.services.jwt.CustomerServiceImpl;
import com.example.jwtangular.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final CustomerServiceImpl customerService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(CustomerServiceImpl customerService, JwtUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String autHeader=request.getHeader("Bearer");
        String token=null;
        String username=null;

        if(autHeader!=null && autHeader.startsWith("Bearer ")){
            token=autHeader.substring(7);
            username= jwtUtil.extractUsername(token);
            if(username !=null && SecurityContextHolder.getContext().getAuthentication() ==null){
                UserDetails userDetails=customerService.loadUserByUsername(username);
                if(jwtUtil.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken=
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }

            }
            filterChain.doFilter(request,response);
        }
    }
}
