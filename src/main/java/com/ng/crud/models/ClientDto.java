package com.ng.crud.models;

import jakarta.validation.constraints.*;

public class ClientDto {
    @NotEmpty(message = "The First Name is required")
    private String firstName;

    @NotEmpty(message = "The last Name is required")
    private String lastName;

    @NotEmpty(message = "The Email is required")
    @Email
    private String email;

    private String phone;
    private String address;

    @NotEmpty(message = "The Status is required")
    private String status;

    public @NotEmpty(message = "The First Name is required") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotEmpty(message = "The First Name is required") String firstName) {
        this.firstName = firstName;
    }

    public @NotEmpty(message = "The Status is required") String getStatus() {
        return status;
    }

    public void setStatus(@NotEmpty(message = "The Status is required") String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public @NotEmpty(message = "The Email is required") @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "The Email is required") @Email String email) {
        this.email = email;
    }

    public @NotEmpty(message = "The last Name is required") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotEmpty(message = "The last Name is required") String lastName) {
        this.lastName = lastName;
    }
}
