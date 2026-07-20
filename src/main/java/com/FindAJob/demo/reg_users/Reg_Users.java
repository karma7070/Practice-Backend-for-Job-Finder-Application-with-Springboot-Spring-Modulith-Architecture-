package com.FindAJob.demo.reg_users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Reg_Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private Gen_Type gender;
    private String profession;
    private String email;

    public Reg_Users(String name,
                     Integer age,
                     Gen_Type gender,
                     String profession,
                     String email){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.profession = profession;
        this.email = email;
    }

    public Reg_Users(){

    }

    public Long getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gen_Type getGender() {
        return gender;
    }

    public void setGender(Gen_Type gender) {
        this.gender = gender;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
