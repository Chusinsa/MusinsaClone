package MusinsaClone.customers;

public record CustomerRequest(
        String username,
        String password,
        String nickname,
        String email,
        String phone,
        String birthdate,
        String address
) {
}
