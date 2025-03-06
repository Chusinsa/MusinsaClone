package MusinsaClone.customers;

// 회원가입용
public record CustomerRequest(
        String username,
        String loginId,
        String password,
        String nickname,
        String email,
        String phone,
        String birthdate,
        String address
) {

}
