package com.FindAJob.demo.companies.internal;

import com.FindAJob.demo.companies.Companies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompRepository extends JpaRepository<Companies, Long> {

 //   public default Companies findByTitle(CompRequestDTO request, Companies comp){

   //     ArrayList<Companies> companies;
     //   for(int i = 0; )
    //}
}
