package com.FindAJob.demo.refreshtoken;

import com.FindAJob.demo.companies.Companies;
import com.FindAJob.demo.reg_users.Reg_Users;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Entity
public class RefreshToken {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private Instant expiresAt;
    private Instant createdAt;

    @ManyToOne
    private Reg_Users user;

    @ManyToOne
    private Companies comp;

    public RefreshToken(String token,
                        Instant expiresAt,
                        Instant createdAt,
                        Reg_Users user){
        this.token = token;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.user = user;
    }

    public RefreshToken(String token,
                        Instant expiresAt,
                        Instant createdAt,
                        Companies comp){
        this.token = token;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.comp = comp;
    }



    public RefreshToken(){


    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Reg_Users getUser() {
        return user;
    }

    public void setUser(Reg_Users user) {
        this.user = user;
    }

    public Companies getComp() {
        return comp;
    }

    public void setComp(Companies comp) {
        this.comp = comp;
    }
}
