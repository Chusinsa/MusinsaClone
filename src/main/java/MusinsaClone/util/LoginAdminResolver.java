package MusinsaClone.util;

import MusinsaClone.admin.Admin;
import MusinsaClone.admin.AdminService;
import org.springframework.stereotype.Component;

@Component
public class LoginAdminResolver {
    private static final String BEARER_PREFIX = "Bearer ";
    public static final String INVALID_TOKEN_MESSAGE = "로그인 정보가 유효하지 않습니다";

    private final JwtProvider jwtProvider;
    private final AdminService adminService;

    public LoginAdminResolver(JwtProvider jwtProvider, AdminService adminService) {
        this.jwtProvider = jwtProvider;
        this.adminService = adminService;
    }

    public Admin resolveAdminFromToken(String bearerToken) {
        String token = extractToken(bearerToken);
        if (!jwtProvider.isValidToken(token)) {
            throw new IllegalArgumentException(INVALID_TOKEN_MESSAGE);
        }
        String loginId = jwtProvider.getSubject(token);
        return adminService.findByLoginId(loginId);
    }

    private String extractToken(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith(BEARER_PREFIX)) {
            throw new IllegalArgumentException(INVALID_TOKEN_MESSAGE);
        }
        return bearerToken.substring(BEARER_PREFIX.length());
    }
}

