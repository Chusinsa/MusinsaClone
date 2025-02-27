package MusinsaClone.customers;

import MusinsaClone.util.SecurityUtils;
import jakarta.transaction.Transactional;
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
    public CustomerResponse login(@Valid CustomerRequest request) {
        String encryptedPassword = SecurityUtils.sha256EncryptHex2(request.password());

        Customer customer = customerRepository.findByLoginId(request.loginId());

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        if (!customer.getPassword().equals(encryptedPassword)) {
            throw new RuntimeException("Invalid password");
        }

        return new CustomerResponse(customer.getLoginId());
    }

        @Transactional
        public CustomerResponse update(Customer customer, @Valid CustomerRequest request) {

            Customer existingCustomer = customerRepository.findById(customer.getId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));


            Customer updatedCustomer = existingCustomer.updateWith(request);


            customerRepository.save(updatedCustomer);


            return new CustomerResponse(updatedCustomer.getLoginId());
        }


    }
