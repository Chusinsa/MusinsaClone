package MusinsaClone.customers;

import MusinsaClone.admin.Admin;
import MusinsaClone.admin.AdminService;
import MusinsaClone.util.JwtProvider;
import org.springframework.stereotype.Component;



@Component
public class LoginCustomerResolver {
    public final CustomerService customerService;
    private static final String BEARER_PREFIX = "Bearer ";
    public static final String INVALID_TOKEN_MESSAGE = "로그인 정보가 유효하지 않습니다";

    private final JwtProvider jwtProvider;


    public LoginCustomerResolver(JwtProvider jwtProvider,  CustomerService customerService) {
        this.jwtProvider = jwtProvider;

        this.customerService = customerService;
    }

    public void resolveCustomerFromToken(String bearerToken) {
        String token = extractToken(bearerToken);
        if (!jwtProvider.isValidToken(token)) {
            throw new IllegalArgumentException(INVALID_TOKEN_MESSAGE);
        }
        String loginId = jwtProvider.getSubject(token);
//        return customerService.findByLoginId(loginId);
    }

    private String extractToken(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith(BEARER_PREFIX)) {
            throw new IllegalArgumentException(INVALID_TOKEN_MESSAGE);
        }
        return bearerToken.substring(BEARER_PREFIX.length());
    }

}
