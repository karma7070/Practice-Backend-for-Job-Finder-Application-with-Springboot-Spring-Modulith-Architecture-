package com.FindAJob.demo.J_Application.internal;

import com.FindAJob.demo.J_Application.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
