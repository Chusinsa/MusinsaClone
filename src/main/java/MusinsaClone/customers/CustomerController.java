package MusinsaClone.customers;

import MusinsaClone.admin.Admin;
//import MusinsaClone.util.LoginMemberResolver;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CustomerController {
    public final CustomerService customerService;
    private final LoginCustomerResolver loginCustomerResolver;


    public CustomerController(CustomerService customersService, LoginCustomerResolver loginCustomerResolver) {
        this.customerService = customersService;
        this.loginCustomerResolver = loginCustomerResolver;

    }

    //회원가입(POST/customers/signup)/RequestBody@valid(요청데이터 자동검증)
    @PostMapping("/customers/signup")
    public void create(@Valid @RequestBody CustomerRequest request) {
        customerService.save(request);
    }


    //로그인(POST/login/customers)/RequestBody
    @PostMapping("/login/customers")
    public CustomerResponse login(@Valid @RequestBody CustomerLoginRequest request) {
        return customerService.login(request);
    }


    //회원 수정(PUT/customers)(토큰으로 받을예정)/RequestBody
    @PutMapping("/customers")
    public void update(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                   @Valid @RequestBody CustomerUpdate request) {
        Customer customer = loginCustomerResolver.resolveCustomerFromToken(token);
        customerService.update(customer, request);
    }

//    //회원 탈퇴(DELETE/customers}/RequestBody/토큰
    @DeleteMapping("/customers")
    public void delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Customer customer = loginCustomerResolver.resolveCustomerFromToken(token);

        customerService.deleteCustomer(customer);

    }
}
