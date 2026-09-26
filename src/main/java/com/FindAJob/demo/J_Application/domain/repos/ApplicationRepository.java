package com.FindAJob.demo.J_Application.domain.repos;

import com.FindAJob.demo.J_Application.domain.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findAllByUserEmail(String email);

    Application findApplicationByJobIdAndUserId(Long id, Long id2);
}
