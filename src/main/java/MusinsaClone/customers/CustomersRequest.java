package MusinsaClone.customers;

public record CustomersRequest(
        String username,
        String password,
        String nickname,
        String email,
        String phone,
        String birthdate,
        String address
) {
}
