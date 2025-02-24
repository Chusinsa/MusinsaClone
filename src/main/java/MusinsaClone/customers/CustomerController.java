package MusinsaClone.customers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    public final CustomerService customerService;

    public CustomerController(CustomerService customersService) {
        this.customerService = customersService;
    }

    //회원가입(POST/customers/signup)/RequestBody@valid(요청데이터 자동검증)
    @PostMapping("/customers/signup")
    public void create(@Valid @RequestBody CustomerRequest request) {
        customerService.save(request);
    }


    //로그인(POST/login/customers)/RequestBody
    @PostMapping("/login/customers")
    public CustomerResponse login(@Valid@RequestBody CustomerRequest request){
        return customerService.login(request);
    }


    //회원 수정(PUT/customers)(토큰으로 받을예정)/RequestBody


    //회원 탈퇴(DELETE/customers}/RequestBody/토큰
}
