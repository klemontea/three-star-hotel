package com.miniproject.threestarhotel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OtpEntity {

    @Id
    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 50)
    private String code;

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
