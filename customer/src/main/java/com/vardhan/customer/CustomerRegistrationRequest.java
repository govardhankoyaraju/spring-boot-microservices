package com.vardhan.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
