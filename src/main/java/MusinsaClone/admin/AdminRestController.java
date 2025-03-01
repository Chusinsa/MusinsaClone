package MusinsaClone.admin;

import MusinsaClone.admin.dto.*;
import MusinsaClone.admin.dto.AdminCreate;
import MusinsaClone.admin.dto.AdminCreateResponse;
import MusinsaClone.util.ApiResponse;
import MusinsaClone.util.LoginAdminResolver;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminRestController {

    private final AdminService adminService;
    private final LoginAdminResolver loginAdminResolver;

    public AdminRestController(AdminService adminService, LoginAdminResolver loginAdminResolver) {
        this.adminService = adminService;
        this.loginAdminResolver = loginAdminResolver;
    }

    @PostMapping("/admins")
    public AdminCreateResponse create(@Valid @RequestBody AdminCreate adminCreate) {
        return adminService.create(adminCreate);
    }

    @PostMapping("/logins/admins")
    public AdminLoginResponse login(@RequestBody AdminLogin adminLogin) {
        return adminService.login(adminLogin);
    }

    @DeleteMapping("/admins")
    public ApiResponse<Void> adminWithdraw(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody AdminWithdrawDTO adminWithdraw) {
        Admin admin = loginAdminResolver.resolveAdminFromToken(token);
        adminService.adminWithdraw(admin, adminWithdraw);
        return ApiResponse.success(null);
    }

    @PutMapping("/admins/my")
    public AdminUpdateResponse adminUpdate(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody AdminUpdate adminUpdate) {
        Admin admin = loginAdminResolver.resolveAdminFromToken(token);
        return adminService.adminUpdate(admin, adminUpdate);
    }

    @PutMapping("/admins/password")
    public ApiResponse<Void> passwordUpdate(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody AdminPasswordUpdate password) {
        Admin admin = loginAdminResolver.resolveAdminFromToken(token);
        adminService.passwordUpdate(admin, password);
        return ApiResponse.success(null);
    }
}
