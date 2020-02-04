package com.spring.cloud.PhotoAppApiUsers.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestedModel {
    @NotNull(message = "first name cannot be null")
    @Size(min = 2,message = "first name must not be less than two characters")
    private String firstName;
    @NotNull(message = "first name cannot be null")
    @Size(min = 2,message = "first name must not be less than two characters")
    private String lastName;
    @NotNull(message = "password cannot be null")
    @Size(min = 8, max = 16,message = "password must be less than 16 characters and greater than 8 characters")
    private String password;
    @NotNull(message = "emaill cannot be null")
    @Email
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
