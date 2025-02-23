package MusinsaClone.admin;

import MusinsaClone.admin.DTO.AdminCreate;
import MusinsaClone.admin.DTO.AdminCreateResponse;
import MusinsaClone.admin.DTO.AdminLogin;
import MusinsaClone.admin.DTO.AdminLoginResponse;
import MusinsaClone.util.JwtProvider;
import MusinsaClone.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final JwtProvider jwtProvider;

    public AdminService(AdminRepository adminRepository, JwtProvider jwtProvider) {
        this.adminRepository = adminRepository;
        this.jwtProvider = jwtProvider;
    }

    public Admin findByLoginId(String loginId) {
        return adminRepository.findByLoginId(loginId).orElseThrow(
                () -> new NoSuchElementException("회원을 찾을 수 없습니다."));
    }

    public AdminCreateResponse create(AdminCreate dto) {
        String hashPassword = SecurityUtils.sha256EncryptHex2(dto.password());
        Admin admin = new Admin(
                dto.loginId(),
                dto.nickName(),
                hashPassword,
                dto.phoneNumber());

        adminRepository.save(admin);

        return new AdminCreateResponse(admin.getId(), admin.getCreatedAt());
    }

    public AdminLoginResponse login(AdminLogin dto) {
        Admin admin = adminRepository.findByLoginId(dto.loginId()).orElseThrow(() ->
                new NoSuchElementException("회원정보가 없습니다."));

        admin.EqualsPassword(dto.password());

        return new AdminLoginResponse(
                admin.getId(),
                admin.getLoginId(),
                jwtProvider.createToken(dto.loginId()));
    }
}
