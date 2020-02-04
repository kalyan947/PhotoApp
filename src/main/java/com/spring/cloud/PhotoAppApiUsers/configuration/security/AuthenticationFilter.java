package com.spring.cloud.PhotoAppApiUsers.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.cloud.PhotoAppApiUsers.model.LoginRequestModel;
import com.spring.cloud.PhotoAppApiUsers.service.UsersService;
import com.spring.cloud.PhotoAppApiUsers.shared.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UsersService usersService;
    private Environment environment;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthenticationFilter(UsersService usersService, Environment environment,
                                AuthenticationManager authenticationManager) {
        this.usersService = usersService;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestModel creds=new ObjectMapper()
                    .readValue(request.getInputStream(),LoginRequestModel.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String userName=((User)authResult.getPrincipal()).getUsername();
        UserDto userDetails=usersService.getUserDetailsByEmail(userName);
        String token= Jwts.builder()
                .setSubject(userDetails.getUserId())
                .setExpiration(new Date(System.currentTimeMillis()+ Long.parseLong(
                        environment.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512,environment.getProperty("token.secret"))
                .compact();
        response.addHeader("token",token);
        response.addHeader("userId",userDetails.getUserId());
    }
}
