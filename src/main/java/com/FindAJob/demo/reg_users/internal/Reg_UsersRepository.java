package com.FindAJob.demo.reg_users.internal;

import com.FindAJob.demo.reg_users.Reg_Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Reg_UsersRepository extends JpaRepository<Reg_Users, Long> {

    Optional<Reg_Users> findByEmail(String email);

}
