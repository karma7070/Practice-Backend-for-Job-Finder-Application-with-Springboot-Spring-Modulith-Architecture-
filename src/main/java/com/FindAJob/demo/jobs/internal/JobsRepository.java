package com.FindAJob.demo.jobs.internal;

import com.FindAJob.demo.companies.Companies;
import com.FindAJob.demo.jobs.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobsRepository extends JpaRepository<Jobs, Long> {

    List<Jobs> findByCompany_id(Long id);

}
