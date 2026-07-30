package com.FindAJob.demo.J_Application.internal;

import com.FindAJob.demo.J_Application.Application;
import com.FindAJob.demo.companies.Companies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByUserEmail(String email);
}
