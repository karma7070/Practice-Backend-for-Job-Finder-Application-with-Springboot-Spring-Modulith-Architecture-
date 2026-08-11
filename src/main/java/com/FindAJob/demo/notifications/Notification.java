package com.FindAJob.demo.notifications;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    public Notification (String name, String email){

        this.name = name;
        this.email = email;

    }

    public Notification(){

    }

    public Long getId(){
        return id;
    }

    public void setName(String name){
       this.name = name;

    }

    public String getName(){
        return name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

}
