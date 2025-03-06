package MusinsaClone.customers;

public record CustomerUpdate(
        String nickname,
        String email,
        String phone,
        String birthdate,
        String address
) {
}
