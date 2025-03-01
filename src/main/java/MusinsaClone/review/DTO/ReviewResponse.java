package MusinsaClone.review.dto;

import MusinsaClone.customers.Customer;

public record ReviewResponse(Customer customer, String title, String detail) {
}
