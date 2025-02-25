package MusinsaClone.customers;

public record CustomerResponse(Long id,
                               String username,
                               String nickname,
                               String email,
                               String phone,
                               String birthdate,
                               String address
                                  ) {
}
