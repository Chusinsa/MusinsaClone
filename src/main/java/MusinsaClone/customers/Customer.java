package MusinsaClone.customers;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String loginId;

    @Column(nullable = false)
    private String username;//유저 이름으로 로그인(ID)

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String birthdate;

    @Column(nullable = false)
    private String address;

    protected Customer() {
    }

    public Customer(String username,String loginId, String password, String nickname, String email, String phone, String birthdate, String address) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.birthdate = birthdate;
        this.address = address;

    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void updateWith(
            String nickname,
            String email,
            String phone,
            String birthdate,
            String address)
    {
        if (nickname != null) {
            this.nickname = nickname;
        }
        this.email = email;
        this. phone = phone;
        this. birthdate = birthdate;
        this. address = address;
    }





}

