package com.FindAJob.demo.jobs.internal;

import com.FindAJob.demo.jobs.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRepository extends JpaRepository<Jobs, Long> {

}
