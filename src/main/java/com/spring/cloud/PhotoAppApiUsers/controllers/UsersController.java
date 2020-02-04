package com.spring.cloud.PhotoAppApiUsers.controllers;

import com.spring.cloud.PhotoAppApiUsers.model.CreateUserRequestedModel;
import com.spring.cloud.PhotoAppApiUsers.model.CreateUserResponseModel;
import com.spring.cloud.PhotoAppApiUsers.service.UsersService;
import com.spring.cloud.PhotoAppApiUsers.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment env;

    @Autowired
    private UsersService usersService;

    @GetMapping("/status/check")
    public String status(){
        return "working on port :"+env.getProperty("local.server.port");
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity createUser(@Valid @RequestBody CreateUserRequestedModel userDetails){
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto=modelMapper.map(userDetails, UserDto.class);
        UserDto createUser=usersService.createUser(userDto);
        CreateUserResponseModel responseModel=modelMapper.map(createUser,CreateUserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }
}
