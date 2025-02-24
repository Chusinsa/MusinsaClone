package MusinsaClone.customers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    public final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //회원가입
    public void save(@Valid CustomerRequest request) {
        customerRepository.save(new Customer(
                request.username(),
                request.password(),
                request.nickname(),
                request.email(),
                request.address(),
                request.phone(),
                request.birthdate()
        ));
    }

    //로그인
    public CustomerResponse login(@Valid CustomerRequest request) {
        return null;
    }


}
