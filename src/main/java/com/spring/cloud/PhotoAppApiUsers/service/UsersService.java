package com.spring.cloud.PhotoAppApiUsers.service;

import com.spring.cloud.PhotoAppApiUsers.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


public interface UsersService extends UserDetailsService {
   UserDto createUser(UserDto userDetails);
   UserDto getUserDetailsByEmail(String email);
}
