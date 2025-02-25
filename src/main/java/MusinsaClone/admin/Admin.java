package MusinsaClone.admin;

import MusinsaClone.util.BaseEntity;
import MusinsaClone.util.SecurityUtils;
import jakarta.persistence.*;

import java.util.NoSuchElementException;

@Entity
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;

    protected Admin() {
    }

    public Admin(String loginId, String nickName, String password, String phoneNumber) {
        this.loginId = loginId;
        this.nickName = nickName;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void EqualsPassword(String password) {
        String hashPassword = SecurityUtils.sha256EncryptHex2(password);
        if (!this.getPassword().equals(hashPassword)) {
            throw new NoSuchElementException("비밀번호가 다릅니다.");
        }
    }

    // setter
    public void updateNickNameAndPhoneNumber(String nickName, String phoneNumber) {
        if (nickName == null && phoneNumber == null) {
            throw new IllegalArgumentException("변경할 내용이 없습니다.");
        }
        if (nickName != null) {
            this.nickName = nickName;
        }
        if (phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }
    }

    public void passwordUpdate(String password) {
        String hashPassword = SecurityUtils.sha256EncryptHex2(password);
        if (this.getPassword().equals(hashPassword)) {
            throw new IllegalArgumentException("똑같은 비밀번호 입니다.");
        }
        this.password = hashPassword;
    }
}
