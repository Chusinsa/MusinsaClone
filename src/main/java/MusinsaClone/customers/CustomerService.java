package MusinsaClone.customers;

import MusinsaClone.util.JwtProvider;
import MusinsaClone.util.SecurityUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    public final CustomerRepository customerRepository;
    public final JwtProvider jwtProvider;

    public CustomerService(CustomerRepository customerRepository, JwtProvider jwtProvider) {
        this.customerRepository = customerRepository;
        this.jwtProvider = jwtProvider;
    }

    //회원가입
    public void save(@Valid CustomerRequest request) {
        String password = SecurityUtils.sha256EncryptHex2(request.password());
        customerRepository.save(new Customer(
                request.username(),
                request.loginId(),
                password,
                request.nickname(),
                request.email(),
                request.address(),
                request.phone(),
                request.birthdate()
        ));
    }

    //로그인
    public CustomerResponse login(@Valid CustomerLoginRequest request) {
        String encryptedPassword = SecurityUtils.sha256EncryptHex2(request.password());

        Customer customer = customerRepository.findByLoginId(request.loginId());

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        if (!customer.getPassword().equals(encryptedPassword)) {
            throw new RuntimeException("Invalid password");
        }

        return new CustomerResponse(customer.getLoginId(),jwtProvider.createToken(request.loginId()));
    }

    @Transactional
    public void update(Customer customer, @Valid CustomerUpdate request) {
        customer.updateWith(
                request.nickname(),
                request.email(),
                request.phone(),
                request.birthdate(),
                request.address()
                );

    }

    @Transactional
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }
}
