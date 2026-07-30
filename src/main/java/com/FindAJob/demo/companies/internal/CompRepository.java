package com.FindAJob.demo.companies.internal;

import com.FindAJob.demo.companies.Companies;
import com.FindAJob.demo.reg_users.Reg_Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompRepository extends JpaRepository<Companies, Long> {

 //   public default Companies findByTitle(CompRequestDTO request, Companies comp){

   //     ArrayList<Companies> companies;
     //   for(int i = 0; )
    //}

    Optional<Companies> findByCompEmail(String compEmail);

}
