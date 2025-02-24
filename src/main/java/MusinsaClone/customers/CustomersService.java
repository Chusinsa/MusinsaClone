package MusinsaClone.customers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CustomersService {
    public final CustomersRepository customersRepository;

    public CustomersService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    //회원가입
    public void save(@Valid CustomersRequest request) {
        customersRepository.save(new Customers(
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
    public CustomersResponse login(@Valid CustomersRequest request) {
        return null;
    }


}
