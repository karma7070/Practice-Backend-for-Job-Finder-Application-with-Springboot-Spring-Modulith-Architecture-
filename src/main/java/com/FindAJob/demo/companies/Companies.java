package com.FindAJob.demo.companies;

import com.FindAJob.demo.SecurityPackage.UserRoles;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Companies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comp_name;
    private String location;
    private String comp_email;
    private String password;
    private UserRoles role;

    public Companies(String comp_name,
                     String location,
                     String comp_email,
                     String password,
                     UserRoles role) {
        this.comp_name = comp_name;
        this.location = location;
        this.comp_email = comp_email;
        this.password = password;
        this.role = role;
    }

    public Companies() {

    }

    public Long getId() {
        return id;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getComp_email() {
        return comp_email;
    }

    public void setComp_email(String comp_email) {
        this.comp_email = comp_email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }
}