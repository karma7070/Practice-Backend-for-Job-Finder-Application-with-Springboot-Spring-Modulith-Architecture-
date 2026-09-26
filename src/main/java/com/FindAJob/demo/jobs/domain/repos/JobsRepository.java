package com.FindAJob.demo.jobs.domain.repos;

import com.FindAJob.demo.jobs.publicenums.JobAvailability;
import com.FindAJob.demo.jobs.publicenums.JobFields;
import com.FindAJob.demo.jobs.domain.entities.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepository extends JpaRepository<Jobs, Long> {

    List<Jobs> findByCompany_id(Long id);

    Jobs findByJobTitle(String title);

    List<Jobs> findAllByField (JobFields field);

    List<Jobs> findAllByJobTitle(String title);

    List<Jobs> findAllByAvailability(JobAvailability avail);

    List<Jobs> findAllByDescription(String des);

}
