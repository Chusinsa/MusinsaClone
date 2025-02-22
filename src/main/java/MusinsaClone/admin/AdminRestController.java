package MusinsaClone.admin;

import MusinsaClone.admin.DTO.AdminCreate;
import MusinsaClone.admin.DTO.AdminCreateResponse;
import MusinsaClone.admin.DTO.AdminLogin;
import MusinsaClone.admin.DTO.AdminLoginResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminRestController {

    private final AdminService adminService;

    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/admins")
    public AdminCreateResponse create(@Valid @RequestBody AdminCreate adminCreate) {
        return adminService.create(adminCreate);
    }
}
