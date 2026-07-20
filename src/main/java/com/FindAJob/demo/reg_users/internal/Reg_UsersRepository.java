package com.FindAJob.demo.reg_users.internal;

import com.FindAJob.demo.reg_users.Reg_Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Reg_UsersRepository extends JpaRepository<Reg_Users, Long> {

}
