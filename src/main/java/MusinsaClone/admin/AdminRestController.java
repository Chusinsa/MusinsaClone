package MusinsaClone.admin;

import MusinsaClone.admin.DTO.*;
import MusinsaClone.util.JwtProvider;
import MusinsaClone.util.LoginMemberResolver;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminRestController {

    private final AdminService adminService;
    private final LoginMemberResolver loginMemberResolver;

    public AdminRestController(AdminService adminService, LoginMemberResolver loginMemberResolver) {
        this.adminService = adminService;
        this.loginMemberResolver = loginMemberResolver;
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
    public void adminWithdraw(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@Valid @RequestBody AdminWithdrawDTO adminWithdraw) {
        Admin admin = loginMemberResolver.resolveUserFromToken(token);
        adminService.adminWithdraw(admin, adminWithdraw);
    }
}
