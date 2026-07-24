package com.FindAJob.demo.reg_users;

import com.FindAJob.demo.reg_users.internal.Reg_UsersRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Reg_UsersConfig implements CommandLineRunner {

    private final Reg_UsersRepository repository;

    public Reg_UsersConfig(Reg_UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String @NonNull ... args) {
        if (repository.count() == 0) {
            List<Reg_Users> users = List.of(
                    new Reg_Users(
                            "Grace Wilson",
                            26,
                            Gen_Type.FEMALE,
                            "Accountant",
                            "grace.wilson@example.com",
                            "ebualnelkna"
                    ),

                    new Reg_Users(
                            "Daniel Smith",
                            31,
                            Gen_Type.MALE,
                            "Lawyer",
                            "daniel.smith@example.com",
                            "ebualnelkna"
                    ),

                    new Reg_Users(
                            "Emily Davis",
                            24,
                            Gen_Type.FEMALE,
                            "Nurse",
                            "emily.davis@example.com",
                            "ebualnelkna"
                    ),

                    new Reg_Users(
                            "James Anderson",
                            29,
                            Gen_Type.MALE,
                            "Backend Developer",
                            "james.anderson@example.com",
                            "ebualnelkna"
                    ),

                    new Reg_Users(
                            "Sophia Taylor",
                            23,
                            Gen_Type.FEMALE,
                            "UI/UX Designer",
                            "sophia.taylor@example.com",
                            "ebualnelkna"
                    ),

                    new Reg_Users(
                            "David Williams",
                            33,
                            Gen_Type.MALE,
                            "Cybersecurity Analyst",
                            "david.williams@example.com",
                            "ebualnelkna"
                    ),

                    new Reg_Users(
                            "Olivia Martinez",
                            30,
                            Gen_Type.FEMALE,
                            "Data Scientist",
                            "olivia.martinez@example.com",
                            "ebualnelkna"
                    )

            );

            repository.saveAll(users);
        }
    }
}